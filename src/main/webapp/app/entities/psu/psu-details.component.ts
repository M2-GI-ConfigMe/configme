import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPsu } from '@/shared/model/psu.model';
import PsuService from './psu.service';

@Component
export default class PsuDetails extends Vue {
  @Inject('psuService') private psuService: () => PsuService;
  public psu: IPsu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.psuId) {
        vm.retrievePsu(to.params.psuId);
      }
    });
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

  public retrievePsu(psuId) {
    this.psuService()
      .find(psuId)
      .then(res => {
        this.psu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
