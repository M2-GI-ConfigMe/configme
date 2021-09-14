import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import { ICpu, Cpu } from '@/shared/model/cpu.model';
import CpuService from './cpu.service';

const validations: any = {
  cpu: {
    frequency: {
      required,
      decimal,
    },
    cacheL1: {},
    cacheL2: {},
    cacheL3: {},
    nbHeart: {
      required,
      numeric,
      min: minValue(1),
    },
    nbThread: {
      required,
      numeric,
      min: minValue(1),
    },
    hasVentirad: {
      required,
    },
    socketType: {
      required,
    },
    lithography: {
      required,
      numeric,
    },
    ramFrequencyMax: {
      required,
      decimal,
    },
    consumption: {
      required,
      numeric,
    },
    hasGpu: {
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
export default class CpuUpdate extends Vue {
  @Inject('cpuService') private cpuService: () => CpuService;
  public cpu: ICpu = new Cpu();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cpuId) {
        vm.retrieveCpu(to.params.cpuId);
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
    if (this.cpu.id) {
      this.cpuService()
        .update(this.cpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.cpu.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.cpu.isActive = true;
      this.cpuService()
        .create(this.cpu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.cpu.created', { param: param.id });
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

  public retrieveCpu(cpuId): void {
    this.cpuService()
      .find(cpuId)
      .then(res => {
        this.cpu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
