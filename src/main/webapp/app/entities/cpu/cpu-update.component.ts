import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import { ICpu, Cpu } from '@/shared/model/cpu.model';
import CpuService from './cpu.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

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
  @Inject('productService') private productService: () => ProductService;
  public cpu: ICpu = new Cpu();
  public isSaving = false;
  public currentLanguage = '';
  public image = null;

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
          const message = this.$t('configmeApp.cpu.updated', { param: param.id });
          if (this.image != null) {
            this.onImageSelected().then(() => {
              this.$router.go(-1);
            });
          } else {
            this.$router.go(-1);
          }
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
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
          this.cpu.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.cpu.created', { param: param.id });
          if (this.image != null) {
            this.onImageSelected().then(() => {
              this.$router.go(-1);
            });
          } else {
            this.$router.go(-1);
          }
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

  public onImageSelected(): Promise<IProduct> {
    const formData = new FormData();
    formData.append('file', this.image);
    return this.productService().updateImg(this.cpu, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.cpu.img = 'updated';
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
