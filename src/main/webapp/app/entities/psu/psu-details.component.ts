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
