import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOrderLine } from '@/shared/model/order-line.model';
import OrderLineService from './order-line.service';

@Component
export default class OrderLineDetails extends Vue {
  @Inject('orderLineService') private orderLineService: () => OrderLineService;
  public orderLine: IOrderLine = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderLineId) {
        vm.retrieveOrderLine(to.params.orderLineId);
      }
    });
  }

  public retrieveOrderLine(orderLineId) {
    this.orderLineService()
      .find(orderLineId)
      .then(res => {
        this.orderLine = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
