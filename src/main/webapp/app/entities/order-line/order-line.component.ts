import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOrderLine } from '@/shared/model/order-line.model';

import OrderLineService from './order-line.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class OrderLine extends Vue {
  @Inject('orderLineService') private orderLineService: () => OrderLineService;
  private removeId: number = null;

  public orderLines: IOrderLine[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOrderLines();
  }

  public clear(): void {
    this.retrieveAllOrderLines();
  }

  public retrieveAllOrderLines(): void {
    this.isFetching = true;
    this.orderLineService()
      .retrieve()
      .then(
        res => {
          this.orderLines = res.data;
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

  public prepareRemove(instance: IOrderLine): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOrderLine(): void {
    this.orderLineService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.orderLine.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOrderLines();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
