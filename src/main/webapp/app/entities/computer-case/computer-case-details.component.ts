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

  public previousState() {
    this.$router.go(-1);
  }
}
