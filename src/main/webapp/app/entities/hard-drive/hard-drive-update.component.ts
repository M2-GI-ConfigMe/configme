import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, decimal, minValue, maxValue } from 'vuelidate/lib/validators';

import { IHardDrive, HardDrive } from '@/shared/model/hard-drive.model';
import HardDriveService from './hard-drive.service';

const validations: any = {
  hardDrive: {
    capacity: {
      required,
      numeric,
    },
    speedWrite: {
      required,
      decimal,
    },
    speedRead: {
      required,
      decimal,
    },
    type: {
      required,
    },
    name: {
      required,
    },
    price: {
      required,
      decimal,
      min: minValue(0.5),
    },
    discount: {
      decimal,
      min: minValue(0),
      max: maxValue(1),
    },
    stock: {
      required,
      numeric,
      min: minValue(0),
    },
    img: {
      required,
    },
    desc: {},
    brand: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class HardDriveUpdate extends Vue {
  @Inject('hardDriveService') private hardDriveService: () => HardDriveService;
  public hardDrive: IHardDrive = new HardDrive();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hardDriveId) {
        vm.retrieveHardDrive(to.params.hardDriveId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.hardDrive.id) {
      this.hardDriveService()
        .update(this.hardDrive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.hardDrive.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.hardDrive.isActive = true;
      this.hardDriveService()
        .create(this.hardDrive)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.hardDrive.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveHardDrive(hardDriveId): void {
    this.hardDriveService()
      .find(hardDriveId)
      .then(res => {
        this.hardDrive = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
