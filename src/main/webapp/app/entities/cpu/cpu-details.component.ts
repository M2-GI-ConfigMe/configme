import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICpu } from '@/shared/model/cpu.model';
import CpuService from './cpu.service';

@Component
export default class CpuDetails extends Vue {
  @Inject('cpuService') private cpuService: () => CpuService;
  public cpu: ICpu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cpuId) {
        vm.retrieveCpu(to.params.cpuId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.cpuService()
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

  public retrieveCpu(cpuId) {
    this.cpuService()
      .find(cpuId)
      .then(res => {
        this.cpu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
