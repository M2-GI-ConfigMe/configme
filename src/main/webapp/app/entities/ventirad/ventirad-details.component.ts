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
