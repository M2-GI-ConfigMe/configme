import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOrder } from '@/shared/model/order.model';

import OrderService from './order.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Order extends Vue {
  @Inject('orderService') private orderService: () => OrderService;
  private removeId: number = null;

  public orders: IOrder[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOrders();
  }

  public clear(): void {
    this.retrieveAllOrders();
  }

  public retrieveAllOrders(): void {
    this.isFetching = true;
    this.orderService()
      .retrieve()
      .then(
        res => {
          this.orders = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IOrder): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOrder(): void {
    this.orderService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.order.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-bottom-right',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOrders();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
