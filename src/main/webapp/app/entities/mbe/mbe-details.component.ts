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
