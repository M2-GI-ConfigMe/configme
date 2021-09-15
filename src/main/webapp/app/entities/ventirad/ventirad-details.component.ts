import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVentirad } from '@/shared/model/ventirad.model';
import VentiradService from './ventirad.service';

@Component
export default class VentiradDetails extends Vue {
  @Inject('ventiradService') private ventiradService: () => VentiradService;
  public ventirad: IVentirad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventiradId) {
        vm.retrieveVentirad(to.params.ventiradId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.ventiradService()
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

  public retrieveVentirad(ventiradId) {
    this.ventiradService()
      .find(ventiradId)
      .then(res => {
        this.ventirad = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
