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

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.psuService()
      .update(product)
      .then(() => {
        this.$bvToast.toast('Produit ' + (isActivated ? 'activé' : 'Désactivé'), {
          toaster: 'b-toaster-bottom-right',
          title: 'Info',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      })
      .catch(() => {
        this.$bvToast.toast('Erreur de la modification du produit', {
          toaster: 'b-toaster-bottom-right',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        product.isActive = !isActivated;
      });
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
          toaster: 'b-toaster-bottom-right',
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
