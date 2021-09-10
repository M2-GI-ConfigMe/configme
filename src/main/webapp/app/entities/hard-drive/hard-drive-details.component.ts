import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHardDrive } from '@/shared/model/hard-drive.model';
import HardDriveService from './hard-drive.service';

@Component
export default class HardDriveDetails extends Vue {
  @Inject('hardDriveService') private hardDriveService: () => HardDriveService;
  public hardDrive: IHardDrive = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hardDriveId) {
        vm.retrieveHardDrive(to.params.hardDriveId);
      }
    });
  }

  public retrieveHardDrive(hardDriveId) {
    this.hardDriveService()
      .find(hardDriveId)
      .then(res => {
        this.hardDrive = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
