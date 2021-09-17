import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import OrderCartRecap from './order-cart-recap.vue';

@Component({
  components: {
    'order-cart-recap': OrderCartRecap,
  },
})
export default class OrderCartProcess extends Vue {
  public e1 = 1;
}
