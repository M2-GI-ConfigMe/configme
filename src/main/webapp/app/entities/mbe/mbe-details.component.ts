import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMbe } from '@/shared/model/mbe.model';
import MbeService from './mbe.service';

@Component
export default class MbeDetails extends Vue {
  @Inject('mbeService') private mbeService: () => MbeService;
  public mbe: IMbe = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mbeId) {
        vm.retrieveMbe(to.params.mbeId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.mbeService()
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

  public retrieveMbe(mbeId) {
    this.mbeService()
      .find(mbeId)
      .then(res => {
        this.mbe = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
