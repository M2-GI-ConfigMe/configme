import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, decimal, minValue, maxValue, numeric } from 'vuelidate/lib/validators';

import { IMbe, Mbe } from '@/shared/model/mbe.model';
import MbeService from './mbe.service';

const validations: any = {
  mbe: {
    socketCpu: {
      required,
    },
    ramType: {
      required,
    },
    ramFrequencyMax: {
      required,
      decimal,
    },
    ramSizeMax: {
      required,
      decimal,
    },
    pciOutputs: {
      required,
    },
    displayOutput: {},
    storageOutput: {},
    insideIO: {},
    backPanelOutput: {},
    bios: {},
    format: {
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
export default class MbeUpdate extends Vue {
  @Inject('mbeService') private mbeService: () => MbeService;
  public mbe: IMbe = new Mbe();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.mbeId) {
        vm.retrieveMbe(to.params.mbeId);
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
    if (this.mbe.id) {
      this.mbeService()
        .update(this.mbe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.mbe.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.mbe.isActive = true;
      this.mbeService()
        .create(this.mbe)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.mbe.created', { param: param.id });
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

  public retrieveMbe(mbeId): void {
    this.mbeService()
      .find(mbeId)
      .then(res => {
        this.mbe = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
