import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import { IGpu, Gpu } from '@/shared/model/gpu.model';
import GpuService from './gpu.service';

const validations: any = {
  gpu: {
    frequency: {
      required,
      decimal,
    },
    memory: {
      required,
      numeric,
    },
    consumption: {
      required,
      numeric,
    },
    clockSpeed: {
      required,
      numeric,
    },
    lithography: {
      required,
      numeric,
    },
    output: {
      required,
    },
    inputPower: {
      required,
    },
    bus: {
      required,
    },
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
export default class GpuUpdate extends Vue {
  @Inject('gpuService') private gpuService: () => GpuService;
  public gpu: IGpu = new Gpu();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gpuId) {
        vm.retrieveGpu(to.params.gpuId);
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
    if (this.gpu.id) {
      this.gpuService()
        .update(this.gpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.gpu.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.gpu.isActive = true;
      this.gpuService()
        .create(this.gpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.gpu.created', { param: param.id });
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

  public retrieveGpu(gpuId): void {
    this.gpuService()
      .find(gpuId)
      .then(res => {
        this.gpu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
