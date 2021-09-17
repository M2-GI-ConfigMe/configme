import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import OrderService from '@/entities/order/order.service';

@Component({})
export default class OrderCartRecap extends Vue {
  @Inject('orderService') private orderService: () => OrderService;

  private cart = null;

  public created() {
    this.orderService()
      .cart()
      .then(res => {
        this.cart = res;
      });
  }
}
