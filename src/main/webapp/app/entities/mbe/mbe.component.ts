import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMbe } from '@/shared/model/mbe.model';

import MbeService from './mbe.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Mbe extends Vue {
  @Inject('mbeService') private mbeService: () => MbeService;
  private removeId: number = null;

  public mbes: IMbe[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMbes();
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.mbeService()
      .update(product)
      .then(() => {
        this.$bvToast.toast('Produit ' + (isActivated ? 'activé' : 'Désactivé'), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      })
      .catch(() => {
        this.$bvToast.toast('Erreur de la modification du produit', {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        product.isActive = !isActivated;
      });
  }

  public clear(): void {
    this.retrieveAllMbes();
  }

  public retrieveAllMbes(): void {
    this.isFetching = true;
    this.mbeService()
      .retrieve()
      .then(
        res => {
          this.mbes = res.data;
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

  public prepareRemove(instance: IMbe): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMbe(): void {
    this.mbeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.mbe.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMbes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
