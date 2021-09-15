import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGpu } from '@/shared/model/gpu.model';
import GpuService from './gpu.service';

@Component
export default class GpuDetails extends Vue {
  @Inject('gpuService') private gpuService: () => GpuService;
  public gpu: IGpu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gpuId) {
        vm.retrieveGpu(to.params.gpuId);
      }
    });
  }

  public setActive(product, isActivated): void {
    product.isActive = isActivated;
    this.gpuService()
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

  public retrieveGpu(gpuId) {
    this.gpuService()
      .find(gpuId)
      .then(res => {
        this.gpu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
