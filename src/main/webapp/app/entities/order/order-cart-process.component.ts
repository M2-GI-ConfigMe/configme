import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import OrderCartRecap from './order-cart-recap.vue';
import OrderService from '@/entities/order/order.service';
import OrderCartAddress from '@/entities/order/order-cart-address.vue';

@Component({
  components: {
    'order-cart-recap': OrderCartRecap,
    'order-cart-address': OrderCartAddress,
  },
})
export default class OrderCartProcess extends Vue {
  @Inject('orderService') private orderService: () => OrderService;

  public state = 3;

  private cart = null;

  public created() {
    this.retrieveCart();
  }

  public retrieveCart() {
    this.orderService()
      .cart()
      .then(res => {
        this.cart = res;
      });
  }

  public goToDeliveryAddress() {
    this.state = 2;
  }

  public goToPayment() {
    this.orderService()
      .update(this.cart)
      .then(() => {
        this.state = 3;
      });
  }

  public comeBack(state) {
    if (this.state > state) this.state = state;
  }

  public pay() {
    this.orderService()
      .pay(this.cart)
      .then(() => {
        this.$router.push('/');
      })
      .catch(error => {
        if (error.response.status == 400) {
          let stock = {};
          let quantityRequired = {};
          // this.cart.forEach(cartLine => {
          //
          // });

          this.state = 1;
        }
      });
  }
}
