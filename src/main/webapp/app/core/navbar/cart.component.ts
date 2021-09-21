import LoginService from '@/account/login.service';
import OrderService from '@/entities/order/order.service';
import { off } from 'process';
import { Component, Inject, Prop, Vue, Watch } from 'vue-property-decorator';

@Component
export default class Cart extends Vue {
  @Inject('loginService') private loginService: () => LoginService;
  @Inject('orderService') private orderService: () => OrderService;

  public showCart = false;
  public showTooltip = false;

  public components = ['computerCase', 'mbe', 'cpu', 'ram1', 'ram2', 'ventirad', 'psu', 'gpu', 'hd1', 'hd2'];

  public get cart(): any {
    const configs = this.$store.getters.cart;
    configs.forEach((el, index, arr) => {
      arr[index] = Object.entries(el).reduce((a, [k, v]) => ((v && this.components.includes(k)) || k == 'name' ? ((a[k] = v), a) : a), {});
    });
    return configs;
  }

  @Watch('cart')
  onCartUpdate(value, oldValue) {
    if (value.length > 0 && !this.showCart) {
      this.showTooltip = true;
      setTimeout(() => {
        this.showTooltip = false;
      }, 2000);
    }
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public removeFromCart(i) {
    this.$store.commit('removeFromCart', i);
  }

  public pay() {
    if (!this.authenticated) {
      this.openLogin();
      return;
    }

    const formattedCart = [];

    this.cart.forEach(config => {
      const formattedConfig = {};
      for (const itm in config) {
        if (this.components.includes(itm)) {
          formattedConfig[itm + 'Id'] = config[itm]['id'];
        }
      }
      formattedCart.push(formattedConfig);
    });

    this.orderService()
      .create(formattedCart)
      .then(() => {
        this.$router.push('/order/cart');
      });
  }

  public get totalPrice() {
    let price = 0;
    this.cart.forEach(item => {
      price += this.getConfigPrice(item);
    });
    return price;
  }

  public get authenticated() {
    return this.$store.getters.authenticated;
  }

  public getConfigPrice(config) {
    return (
      (config.computerCase ? config.computerCase.price : 0) +
      (config.mbe ? config.mbe.price : 0) +
      (config.ram1 ? config.ram1.price : 0) +
      (config.gpu ? config.gpu.price : 0) +
      (config.cpu ? config.cpu.price : 0) +
      (config.psu ? config.psu.price : 0) +
      (config.ventirad ? config.ventirad.price : 0) +
      (config.hd1 ? config.hd1.price : 0) +
      (config.hd2 ? config.hd2.price : 0)
    );
  }
}
