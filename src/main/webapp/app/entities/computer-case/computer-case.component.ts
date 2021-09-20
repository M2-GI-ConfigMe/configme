import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IComputerCase } from '@/shared/model/computer-case.model';

import ComputerCaseService from './computer-case.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ComputerCase extends Vue {
  @Inject('computerCaseService') private computerCaseService: () => ComputerCaseService;
  private removeId: number = null;

  public computerCases: IComputerCase[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllComputerCases();
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.computerCaseService()
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
    this.retrieveAllComputerCases();
  }

  public retrieveAllComputerCases(): void {
    this.isFetching = true;
    this.computerCaseService()
      .retrieve()
      .then(
        res => {
          this.computerCases = res.data.content.content;
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

  public prepareRemove(instance: IComputerCase): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeComputerCase(): void {
    this.computerCaseService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.computerCase.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-bottom-right',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllComputerCases();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
