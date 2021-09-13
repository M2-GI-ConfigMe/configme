import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue } from 'vuelidate/lib/validators';

import { IComputerCase, ComputerCase } from '@/shared/model/computer-case.model';
import ComputerCaseService from './computer-case.service';

const validations: any = {
  computerCase: {
    type: {
      required,
    },
    formats: {},
    sizeMaxGpu: {
      required,
      numeric,
    },
    sizeMaxVentirad: {
      required,
      numeric,
    },
    sizeMaxPsu: {
      required,
      numeric,
    },
    hardDriveSlots: {},
    frontPanelOutputs: {},
    fanIncluded: {},
    fanSlotsAvailable: {},
    watercoolingCompatibility: {},
    dimension: {
      height: {
        required,
        numeric,
        min: minValue(0),
      },
      width: {
        required,
        numeric,
        min: minValue(0),
      },
      length: {
        required,
        numeric,
        min: minValue(0),
      },
    },
  },
};

@Component({
  validations,
})
export default class ComputerCaseUpdate extends Vue {
  @Inject('computerCaseService') private computerCaseService: () => ComputerCaseService;
  public computerCase: IComputerCase = new ComputerCase();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.computerCaseId) {
        vm.retrieveComputerCase(to.params.computerCaseId);
      }
      vm.initRelationships();
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
    if (this.computerCase.id) {
      this.computerCaseService()
        .update(this.computerCase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.computerCase.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.computerCaseService()
        .create(this.computerCase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.computerCase.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveComputerCase(computerCaseId): void {
    this.computerCaseService()
      .find(computerCaseId)
      .then(res => {
        this.computerCase = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
