import UserOAuth2Service from '@/entities/user/user.oauth2.service';
import { IClientConfig } from '@/shared/model/client-config.model';
import { IOrder } from '@/shared/model/order.model';
import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';

@Component({})
export default class Orders extends Vue {
  @Inject('userOAuth2Service') private userOAuth2Service: () => UserOAuth2Service;

  public orders: IOrder[] = [];

  public status = {
    FAILED: {
      name: 'Erreur',
      color: 'red',
    },
    PAYED: {
      name: 'Payé',
      color: 'success',
    },
    PROCESSING: {
      name: 'En cours',
      color: 'orange',
    },
  };

  created() {
    this.userOAuth2Service()
      .orders(this.$store.getters.account.id)
      .then(res => {
        this.orders = res.data;
      });
  }

  public getOrderPrice(order) {
    let price = 0;
    order.lines.forEach(l => {
      price += this.getConfigPrice(l.config);
    });
    return price;
  }

  public getConfigPrice(config: IClientConfig) {
    return (
      config.cpuPrice +
      config.gpuPrice +
      config.ram1Price +
      config.ram2Price +
      config.psuPrice +
      config.ventiradPrice +
      config.computerCasePrice +
      config.hd1Price +
      config.hd2Price +
      config.mbePrice
    );
  }

  public getFilteredConfig(config) {
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
}
