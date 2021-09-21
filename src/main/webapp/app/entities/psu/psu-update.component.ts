import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, decimal, maxValue } from 'vuelidate/lib/validators';

import { IPsu, Psu } from '@/shared/model/psu.model';
import PsuService from './psu.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

const validations: any = {
  psu: {
    power: {
      required,
      numeric,
    },
    certification: {},
    modularity: {
      required,
    },
    nbSata: {
      required,
      numeric,
      min: minValue(1),
    },
    nbPciE: {
      required,
      numeric,
      min: minValue(1),
    },
    outputs: {
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
export default class PsuUpdate extends Vue {
  @Inject('psuService') private psuService: () => PsuService;
  @Inject('productService') private productService: () => ProductService;
  public psu: IPsu = new Psu();
  public isSaving = false;
  public currentLanguage = '';
  public image = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.psuId) {
        vm.retrievePsu(to.params.psuId);
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
    if (this.psu.id) {
      this.psuService()
        .update(this.psu)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('configmeApp.psu.updated', { param: param.id });
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
      this.psu.isActive = true;
      this.psuService()
        .create(this.psu)
        .then(param => {
          this.psu.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.psu.created', { param: param.id });
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
    return this.productService().updateImg(this.psu, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.psu.img = 'updated';
  }

  public retrievePsu(psuId): void {
    this.psuService()
      .find(psuId)
      .then(res => {
        this.psu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
