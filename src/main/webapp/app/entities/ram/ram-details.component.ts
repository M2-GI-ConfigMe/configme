import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRam } from '@/shared/model/ram.model';
import RamService from './ram.service';

@Component
export default class RamDetails extends Vue {
  @Inject('ramService') private ramService: () => RamService;
  public ram: IRam = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ramId) {
        vm.retrieveRam(to.params.ramId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.ramService()
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

  public retrieveRam(ramId) {
    this.ramService()
      .find(ramId)
      .then(res => {
        this.ram = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
