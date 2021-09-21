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

  public state = 1;

  private cart = null;

  private errorMessage = '';

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
          this.checkStock();
          this.state = 1;
        }
      });
  }

  public checkStock() {
    let message = '';
    const stock = {};
    const quantityRequired = {};
    this.cart.lines.forEach(cartLine => {
      const config = cartLine.config;
      Object.keys(config).forEach(key => {
        if (typeof config[key] === 'object' && config[key] != null && key != 'user') {
          if (quantityRequired[config[key].name] != undefined) quantityRequired[config[key].name]++;
          else quantityRequired[config[key].name] = 1;

          if (stock[config[key].name] == undefined) stock[config[key].name] = config[key].stock;
        }
      });
    });

    Object.keys(quantityRequired).forEach(key => {
      if (stock[key] < quantityRequired[key])
        message += (message.length ? '\n' : '') + 'Le produit ' + key + " n'a plus que " + stock[key] + ' exemplaire(s) en stock';
    });

    this.errorMessage = message;
  }
}
