import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHardDrive } from '@/shared/model/hard-drive.model';

import HardDriveService from './hard-drive.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class HardDrive extends Vue {
  @Inject('hardDriveService') private hardDriveService: () => HardDriveService;
  private removeId: number = null;

  public hardDrives: IHardDrive[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHardDrives();
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.hardDriveService()
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
    this.retrieveAllHardDrives();
  }

  public retrieveAllHardDrives(): void {
    this.isFetching = true;
    this.hardDriveService()
      .retrieve()
      .then(
        res => {
          this.hardDrives = res.data.content;
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

  public prepareRemove(instance: IHardDrive): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeHardDrive(): void {
    this.hardDriveService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.hd.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-bottom-right',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllHardDrives();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
