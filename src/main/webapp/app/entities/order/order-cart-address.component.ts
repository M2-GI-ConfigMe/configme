import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';

@Component
export default class OrderCartAddress extends Vue {
  @Prop() private cart: any;
}
