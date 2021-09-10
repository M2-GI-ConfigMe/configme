import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import DimensionService from '@/entities/dimension/dimension.service';
import { IDimension } from '@/shared/model/dimension.model';

import { IVentirad, Ventirad } from '@/shared/model/ventirad.model';
import VentiradService from './ventirad.service';

const validations: any = {
  ventirad: {
    rangeFanSpeed: {
      required,
    },
    noise: {},
    hasThermalPaste: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class VentiradUpdate extends Vue {
  @Inject('ventiradService') private ventiradService: () => VentiradService;
  public ventirad: IVentirad = new Ventirad();

  @Inject('dimensionService') private dimensionService: () => DimensionService;

  public dimensions: IDimension[] = [];
  public isSaving = false;
  public currentLanguage = '';

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
    if (this.ventirad.id) {
      this.ventiradService()
        .update(this.ventirad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.ventirad.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ventiradService()
        .create(this.ventirad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.ventirad.created', { param: param.id });
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

  public retrieveVentirad(ventiradId): void {
    this.ventiradService()
      .find(ventiradId)
      .then(res => {
        this.ventirad = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.dimensionService()
      .retrieve()
      .then(res => {
        this.dimensions = res.data;
      });
  }
}
