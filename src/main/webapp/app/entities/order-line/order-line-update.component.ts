import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import ClientConfigService from '@/entities/client-config/client-config.service';
import { IClientConfig } from '@/shared/model/client-config.model';

import OrderService from '@/entities/order/order.service';
import { IOrder } from '@/shared/model/order.model';

import { IOrderLine, OrderLine } from '@/shared/model/order-line.model';
import OrderLineService from './order-line.service';

const validations: any = {
  orderLine: {
    config: {
      required,
    },
    order: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OrderLineUpdate extends Vue {
  @Inject('orderLineService') private orderLineService: () => OrderLineService;
  public orderLine: IOrderLine = new OrderLine();

  @Inject('clientConfigService') private clientConfigService: () => ClientConfigService;

  public clientConfigs: IClientConfig[] = [];

  @Inject('orderService') private orderService: () => OrderService;

  public orders: IOrder[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderLineId) {
        vm.retrieveOrderLine(to.params.orderLineId);
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
    if (this.orderLine.id) {
      this.orderLineService()
        .update(this.orderLine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.orderLine.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.orderLineService()
        .create(this.orderLine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('configmeApp.orderLine.created', { param: param.id });
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

  public retrieveOrderLine(orderLineId): void {
    this.orderLineService()
      .find(orderLineId)
      .then(res => {
        this.orderLine = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clientConfigService()
      .retrieve()
      .then(res => {
        this.clientConfigs = res.data;
      });
    this.orderService()
      .retrieve()
      .then(res => {
        this.orders = res.data;
      });
  }
}
