import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import OrderCartConfig from '@/entities/order/order-cart-config.vue';

@Component({
  components: {
    OrderCartConfig,
  },
})
export default class OrderCartRecap extends Vue {
  @Prop() private cart: any;

  public get globalPrice() {
    if (this.cart) return this.cart.lines.reduce((oldValue, newValue) => oldValue + this.configPrice(newValue.config), 0);
    else return 0;
  }

  public configPrice(config: any) {
    let price = 0;

    if (config.cpu) price += config.cpuPrice;
    if (config.gpu) price += config.gpuPrice;
    if (config.computerCase) price += config.computerCasePrice;
    if (config.ventirad) price += config.ventiradPrice;
    if (config.ram1) price += config.ram1Price;
    if (config.ram2) price += config.ram2Price;
    if (config.hd1) price += config.hd1Price;
    if (config.hd2) price += config.hd2Price;
    if (config.mbe) price += config.cpuPrice;
    if (config.psu) price += config.psuPrice;

    return price;
  }

  public onConfigUpdate(index) {
    this.$emit('onLineRemoved', index);
  }
}
