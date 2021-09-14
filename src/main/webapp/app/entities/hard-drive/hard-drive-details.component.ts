import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHardDrive } from '@/shared/model/hard-drive.model';
import HardDriveService from './hard-drive.service';

@Component
export default class HardDriveDetails extends Vue {
  @Inject('hardDriveService') private hardDriveService: () => HardDriveService;
  public hardDrive: IHardDrive = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hardDriveId) {
        vm.retrieveHardDrive(to.params.hardDriveId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.hardDriveService()
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

  public retrieveHardDrive(hardDriveId) {
    this.hardDriveService()
      .find(hardDriveId)
      .then(res => {
        this.hardDrive = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
