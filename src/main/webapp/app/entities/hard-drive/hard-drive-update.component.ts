import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, decimal, minValue, maxValue } from 'vuelidate/lib/validators';

import { IHardDrive, HardDrive } from '@/shared/model/hard-drive.model';
import HardDriveService from './hard-drive.service';
import ProductService from '../product/product.service';
import { IProduct } from '@/shared/model/product.model';

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
  @Inject('productService') private productService: () => ProductService;
  public hardDrive: IHardDrive = new HardDrive();
  public isSaving = false;
  public currentLanguage = '';
  public image = null;

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
          const message = this.$t('configmeApp.hardDrive.updated', { param: param.id });
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
      this.hardDrive.isActive = true;
      this.hardDriveService()
        .create(this.hardDrive)
        .then(param => {
          this.hardDrive.id = param.id;
          this.isSaving = false;
          const message = this.$t('configmeApp.hardDrive.created', { param: param.id });
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
    return this.productService().updateImg(this.hardDrive, formData);
  }

  public selectFile(file) {
    this.image = file;
    this.hardDrive.img = 'updated';
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
