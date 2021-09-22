import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import ClientConfigService from '@/entities/client-config/client-config.service';
import OrderLineService from '@/entities/order-line/order-line.service';

@Component
export default class OrderCartConfig extends Vue {
  @Inject('clientConfigService') private configService: () => ClientConfigService;
  @Inject('orderLineService') private orderLineService: () => OrderLineService;

  @Prop() private orderLine?: any;
  @Prop() private index: number;

  private config = null;

  public created() {
    this.config = this.orderLine.config;
  }

  public get products() {
    const config = this.config;
    const products = [];
    if (config.cpu) products.push({ product: config.cpu, price: config.cpuPrice, productName: 'cpu' });
    if (config.gpu) products.push({ product: config.gpu, price: config.gpuPrice, productName: 'gpu' });
    if (config.computerCase) products.push({ product: config.computerCase, price: config.computerCasePrice, productName: 'computerCase' });
    if (config.ventirad) products.push({ product: config.ventirad, price: config.ventiradPrice, productName: 'ventirad' });
    if (config.ram1) products.push({ product: config.ram1, price: config.ram1Price, productName: 'ram1' });
    if (config.ram2) products.push({ product: config.ram2, price: config.ram2Price, productName: 'ram2' });
    if (config.hd1) products.push({ product: config.hd1, price: config.hd1Price, productName: 'hd1' });
    if (config.hd2) products.push({ product: config.hd2, price: config.hd2Price, productName: 'hd2' });
    if (config.mbe) products.push({ product: config.mbe, price: config.cpuPrice, productName: 'mbe' });
    if (config.psu) products.push({ product: config.psu, price: config.psuPrice, productName: 'psu' });

    return products;
  }

  public get totalPrice() {
    return this.products.reduce((oldValue, newValue) => oldValue + newValue.price, 0);
  }

  public removeProduct(productLine) {
    if (window.confirm('Voulez vous vraiment retirer cet article du panier ? \n Article: ' + productLine.product.name)) {
      this.configService()
        .update(this.config)
        .then(res => {
          this.config[productLine.productName] = null;

          if (productLine.productName == 'hd1') this.config.hd1Price = 0;
          else if (productLine.productName == 'hd2') this.config.hd2Price = 0;
          else this.config[productLine.productName + 'Price'] = 0;

          this.$bvToast.toast('Article: ' + productLine.product.name + ' retiré du panier', {
            toaster: 'b-toaster-top-right',
            title: 'Info',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(res => {
          this.$root.$bvToast.toast('Erreur lors de la mise à jour de votre panier', {
            toaster: 'b-toaster-top-right',
            variant: 'danger',
            solid: true,
            noCloseButton: true,
          });
        });
    }
  }

  public removeConfig() {
    if (window.confirm('Voulez vous vraiment retirer cette configuration du panier ?'))
      this.orderLineService()
        .delete(this.orderLine.id)
        .then(res => {
          this.$emit('configUpdated', this.index);
          this.$bvToast.toast('Configuration retiré du panier', {
            toaster: 'b-toaster-top-right',
            title: 'Info',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
  }
}
