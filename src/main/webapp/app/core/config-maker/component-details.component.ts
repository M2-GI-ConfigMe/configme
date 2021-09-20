import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';

@Component({})
export default class ComponentDetails extends Vue {
  @Prop({ required: true }) component: any;
  @Prop({ required: true }) componentName: string;

  private unwantedFields = ['id', 'price', 'isActive', 'discount', 'img', 'name', 'brand', 'stock', 'desc'];

  public get filteredComponent(): any {
    return Object.entries(this.component).reduce((a, [k, v]) => (v && !this.unwantedFields.includes(k) ? ((a[k] = v), a) : a), {});
  }

  public close() {
    this.$emit('close');
  }
}
