import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import { IGpu, Gpu } from '@/shared/model/gpu.model';
import GpuService from './gpu.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

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
        numeric,
        min: minValue(0),
      },
      width: {
        numeric,
        min: minValue(0),
      },
      length: {
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
  @Inject('productService') private productService: () => ProductService;
  public gpu: IGpu = new Gpu();
  public isSaving = false;
  public currentLanguage = '';
  public image = null;

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
          const message = this.$t('configmeApp.gpu.updated', { param: param.id });
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
      this.gpu.isActive = true;
      this.gpuService()
        .create(this.gpu)
        .then(param => {
          this.gpu.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.gpu.created', { param: param.id });
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
    if (this.image != null) this.onImageSelected();
  }

  public onImageSelected(): Promise<IProduct> {
    const formData = new FormData();
    formData.append('file', this.image);
    return this.productService().updateImg(this.gpu, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.gpu.img = 'updated';
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
