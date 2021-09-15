import { Component, Vue, Inject } from 'vue-property-decorator';

import { IComputerCase } from '@/shared/model/computer-case.model';
import ComputerCaseService from './computer-case.service';

@Component
export default class ComputerCaseDetails extends Vue {
  @Inject('computerCaseService') private computerCaseService: () => ComputerCaseService;
  public computerCase: IComputerCase = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.computerCaseId) {
        vm.retrieveComputerCase(to.params.computerCaseId);
      }
    });
  }

  public retrieveComputerCase(computerCaseId) {
    this.computerCaseService()
      .find(computerCaseId)
      .then(res => {
        this.computerCase = res;
      });
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

  public previousState() {
    this.$router.go(-1);
  }
}
