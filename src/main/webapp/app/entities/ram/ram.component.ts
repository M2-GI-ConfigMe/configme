import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRam } from '@/shared/model/ram.model';

import RamService from './ram.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Ram extends Vue {
  @Inject('ramService') private ramService: () => RamService;
  private removeId: number = null;

  public rams: IRam[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRams();
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.ramService()
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
    this.retrieveAllRams();
  }

  public retrieveAllRams(): void {
    this.isFetching = true;
    this.ramService()
      .retrieve()
      .then(
        res => {
          this.rams = res.data;
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

  public prepareRemove(instance: IRam): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRam(): void {
    this.ramService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.ram.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRams();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
