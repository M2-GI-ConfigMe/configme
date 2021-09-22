import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';

@Component
export default class OrderCartConfirmation extends Vue {
  @Prop() private order?: any;
}
