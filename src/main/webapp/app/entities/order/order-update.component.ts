import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AddressService from '@/entities/address/address.service';
import { IAddress } from '@/shared/model/address.model';

import { IOrder, Order } from '@/shared/model/order.model';
import OrderService from './order.service';

const validations: any = {
  order: {
    createdAt: {
      required,
    },
    updatedAt: {
      required,
    },
    validatedAt: {
      required,
    },
    status: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OrderUpdate extends Vue {
  @Inject('orderService') private orderService: () => OrderService;
  public order: IOrder = new Order();

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderId) {
        vm.retrieveOrder(to.params.orderId);
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
    if (this.order.id) {
      this.orderService()
        .update(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.order.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-bottom-right',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.orderService()
        .create(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.order.created', { param: param.id });
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

  public retrieveOrder(orderId): void {
    this.orderService()
      .find(orderId)
      .then(res => {
        this.order = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.addressService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
  }
}
