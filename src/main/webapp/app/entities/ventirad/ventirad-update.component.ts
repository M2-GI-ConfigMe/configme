import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, maxValue, minValue, numeric, required } from 'vuelidate/lib/validators';

import { IVentirad, Ventirad } from '@/shared/model/ventirad.model';
import { SocketType } from '@/shared/model/enumerations/socket-type.model';
import VentiradService from './ventirad.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

const validations: any = {
  ventirad: {
    rangeFanSpeed: {
      required,
    },
    noise: {},
    hasThermalPaste: {
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
export default class VentiradUpdate extends Vue {
  @Inject('ventiradService') private ventiradService: () => VentiradService;
  @Inject('productService') private productService: () => ProductService;
  public ventirad: IVentirad = new Ventirad();

  public isSaving = false;
  public currentLanguage = '';

  public socketTypes = SocketType;
  public sockets = [];
  public image = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventiradId) {
        vm.retrieveVentirad(to.params.ventiradId);
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
    this.ventirad.sockets = this.sockets;
    if (this.ventirad.id) {
      this.ventiradService()
        .update(this.ventirad)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('configmeApp.ventirad.updated', { param: param.id });
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
      this.ventirad.isActive = true;
      this.ventiradService()
        .create(this.ventirad)
        .then(param => {
          this.ventirad.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.ventirad.created', { param: param.id });
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
    return this.productService().updateImg(this.ventirad, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.ventirad.img = 'updated';
  }

  public retrieveVentirad(ventiradId): void {
    this.ventiradService()
      .find(ventiradId)
      .then(res => {
        this.ventirad = res;
        res.sockets.forEach(socket => this.sockets.push(socket));
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
