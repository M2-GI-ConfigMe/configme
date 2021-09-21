import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, decimal, minValue, maxValue } from 'vuelidate/lib/validators';

import { IRam, Ram } from '@/shared/model/ram.model';
import RamService from './ram.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

const validations: any = {
  ram: {
    speed: {
      required,
      numeric,
    },
    type: {
      required,
    },
    frequency: {
      min: minValue(2),
      required,
      decimal,
    },
    unitSize: {
      required,
      numeric,
    },
    quantity: {
      required,
      numeric,
      min: minValue(2),
      max: maxValue(8),
    },
    cas: {
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
export default class RamUpdate extends Vue {
  @Inject('ramService') private ramService: () => RamService;
  @Inject('productService') private productService: () => ProductService;
  public ram: IRam = new Ram();
  public isSaving = false;
  public currentLanguage = '';
  public image = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ramId) {
        vm.retrieveRam(to.params.ramId);
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
    if (this.ram.id) {
      this.ramService()
        .update(this.ram)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('configmeApp.ram.updated', { param: param.id });
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
      this.ram.isActive = true;
      this.ramService()
        .create(this.ram)
        .then(param => {
          this.ram.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.ram.created', { param: param.id });
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
    return this.productService().updateImg(this.ram, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.ram.img = 'updated';
  }

  public retrieveRam(ramId): void {
    this.ramService()
      .find(ramId)
      .then(res => {
        this.ram = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
