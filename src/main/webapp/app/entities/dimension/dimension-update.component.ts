import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import { IDimension, Dimension } from '@/shared/model/dimension.model';
import DimensionService from './dimension.service';

const validations: any = {
  dimension: {
    height: {
      required,
      numeric,
    },
    width: {
      required,
      numeric,
    },
    length: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class DimensionUpdate extends Vue {
  @Inject('dimensionService') private dimensionService: () => DimensionService;
  public dimension: IDimension = new Dimension();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dimensionId) {
        vm.retrieveDimension(to.params.dimensionId);
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
    if (this.dimension.id) {
      this.dimensionService()
        .update(this.dimension)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.dimension.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dimensionService()
        .create(this.dimension)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.dimension.created', { param: param.id });
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

  public retrieveDimension(dimensionId): void {
    this.dimensionService()
      .find(dimensionId)
      .then(res => {
        this.dimension = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
