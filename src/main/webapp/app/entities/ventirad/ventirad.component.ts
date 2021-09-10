import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVentirad } from '@/shared/model/ventirad.model';

import VentiradService from './ventirad.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Ventirad extends Vue {
  @Inject('ventiradService') private ventiradService: () => VentiradService;
  private removeId: number = null;

  public ventirads: IVentirad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVentirads();
  }

  public clear(): void {
    this.retrieveAllVentirads();
  }

  public retrieveAllVentirads(): void {
    this.isFetching = true;
    this.ventiradService()
      .retrieve()
      .then(
        res => {
          this.ventirads = res.data;
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

  public prepareRemove(instance: IVentirad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVentirad(): void {
    this.ventiradService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.ventirad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVentirads();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
