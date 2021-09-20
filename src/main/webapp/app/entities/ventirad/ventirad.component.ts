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

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.ventiradService()
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
    this.retrieveAllVentirads();
  }

  public retrieveAllVentirads(): void {
    this.isFetching = true;
    this.ventiradService()
      .retrieve()
      .then(
        res => {
          this.ventirads = res.data.content;
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
          toaster: 'b-toaster-bottom-right',
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
