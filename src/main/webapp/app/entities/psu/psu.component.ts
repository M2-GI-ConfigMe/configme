import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPsu } from '@/shared/model/psu.model';

import PsuService from './psu.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Psu extends Vue {
  @Inject('psuService') private psuService: () => PsuService;
  private removeId: number = null;

  public psus: IPsu[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPsus();
  }

  public clear(): void {
    this.retrieveAllPsus();
  }

  public retrieveAllPsus(): void {
    this.isFetching = true;
    this.psuService()
      .retrieve()
      .then(
        res => {
          this.psus = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPsu): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePsu(): void {
    this.psuService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.psu.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPsus();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
