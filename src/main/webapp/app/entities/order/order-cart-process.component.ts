import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import OrderCartRecap from './order-cart-recap.vue';
import OrderService from '@/entities/order/order.service';
import OrderCartAddress from '@/entities/order/order-cart-address.vue';
import OrderCartConfirmation from '@/entities/order/order-cart-confirmation.vue';

@Component({
  components: {
    'order-cart-recap': OrderCartRecap,
    'order-cart-address': OrderCartAddress,
    'order-cart-confirmation': OrderCartConfirmation,
  },
})
export default class OrderCartProcess extends Vue {
  @Inject('orderService') private orderService: () => OrderService;

  public state = 1;

  private cart = null;

  private errorMessage = '';

  public confirmedOrder = null;
  public loading = false;
  public created() {
    this.retrieveCart();
  }

  public retrieveCart() {
    this.loading = true;
    this.orderService()
      .cart()
      .then(res => {
        if (res) this.cart = res;
        else this.$router.push('/');
      })
      .finally(() => {
        this.loading = false;
      });
  }

  public goToDeliveryAddress() {
    this.state = 2;
  }

  public goToPayment() {
    this.loading = true;
    this.orderService()
      .update(this.cart)
      .then(() => {
        this.state = 3;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  public onLineRemoved(index) {
    console.log(index);
    // this.$delete(this.cart.lines, index)
    console.log(this.cart.lines[index]);
    // this.cart.lines.splice(index, 1);
    Vue.delete(this.cart.lines, index);
  }

  public comeBack(state) {
    if (this.state > state) this.state = state;
  }

  public pay() {
    this.loading = true;
    this.orderService()
      .pay(this.cart)
      .then(res => {
        this.confirmedOrder = res;
        this.$store.commit('emptyCart');
        this.state = 4;
      })
      .catch(error => {
        if (error.response.status == 400) {
          this.checkStock();
          this.state = 1;
        }
      })
      .finally(() => {
        this.loading = false;
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
