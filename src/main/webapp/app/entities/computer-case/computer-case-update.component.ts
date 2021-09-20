import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue, decimal, maxValue } from 'vuelidate/lib/validators';

import { IComputerCase, ComputerCase } from '@/shared/model/computer-case.model';
import ComputerCaseService from './computer-case.service';
import { FormatType } from '@/shared/model/enumerations/format-type.model';
import { data } from 'autoprefixer';
import { Dimension } from '@/shared/model/dimension.model';

const validations: any = {
  computerCase: {
    type: {
      required,
    },
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
export default class ComputerCaseUpdate extends Vue {
  @Inject('computerCaseService') private computerCaseService: () => ComputerCaseService;
  public computerCase: IComputerCase = new ComputerCase();

  public formatTypes = FormatType;
  public formats = [];

  public isSaving = false;
  public currentLanguage = '';
  public image;

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
    this.computerCase.formats = this.formats;
    if (this.computerCase.id) {
      this.computerCaseService()
        .update(this.computerCase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.computerCase.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.computerCase.isActive = true;
      this.computerCaseService()
        .create(this.computerCase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.computerCase.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
    if (this.image != null) this.onImageSelected();
  }

  public onImageSelected(): void {
    const formData = new FormData();
    formData.append('file', this.image);
    this.computerCaseService().updateImg(this.computerCase, formData);
  }

  public selectFile(file) {
    this.image = file;
  }

  public retrieveComputerCase(computerCaseId): void {
    this.computerCaseService()
      .find(computerCaseId)
      .then(res => {
        this.computerCase = res;
        res.formats.forEach(format => this.formats.push(format));
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
