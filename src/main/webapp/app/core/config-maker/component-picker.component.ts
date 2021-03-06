import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import axios from 'axios';
import ComponentDetails from './component-details.vue';

const baseApiUrl = 'api/';

@Component({
  components: {
    ComponentDetails,
  },
})
export default class ComponentPicker extends Vue {
  @Prop({ required: true }) activated: boolean;
  @Prop({ required: true }) componentName: string;

  private componentTableInfo = {
    mbe: {
      displayName: 'Cartes mère',
      apiEndpoint: 'mbes',
      headers: [
        {
          text: 'Nom',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Socket',
          value: 'socketCpu',
        },
        {
          text: 'Ram Type',
          value: 'ramType',
        },
        {
          text: 'Format',
          value: 'format',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    cpu: {
      displayName: 'Processeurs',
      apiEndpoint: 'cpus',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Socket',
          value: 'socketType',
        },
        {
          text: 'Ventirad inclu',
          value: 'hasVentirad',
        },
        {
          text: 'GPU inclu',
          value: 'hasGpu',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    ventirad: {
      displayName: 'Ventirads',
      apiEndpoint: 'ventirads',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Pâte thermique inclue',
          value: 'hasThermalPaste',
          align: 'start',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    ram1: {
      displayName: 'RAMs',
      apiEndpoint: 'rams',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Fréquence',
          value: 'frequency',
        },
        {
          text: 'Quantité',
          value: 'quantity',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    gpu: {
      displayName: 'GPUs',
      apiEndpoint: 'gpus',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Mémoire',
          value: 'memory',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    psu: {
      displayName: 'Alimentations',
      apiEndpoint: 'psus',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Modularité',
          value: 'modularityType',
        },
        {
          text: 'Puissance',
          value: 'power',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    computerCase: {
      displayName: 'Boîtiers',
      apiEndpoint: 'computer-cases',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Type',
          value: 'type',
        },
        {
          text: 'Formats',
          value: 'formats',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    hd1: {
      displayName: 'Stockage',
      apiEndpoint: 'hard-drives',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Capacité',
          value: 'capacity',
        },
        {
          text: 'Type',
          value: 'type',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
    hd2: {
      displayName: 'Stockage',
      apiEndpoint: 'hard-drives',
      headers: [
        {
          text: 'Nom',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Marque',
          value: 'brand',
        },
        {
          text: 'Capacité',
          value: 'capacity',
        },
        {
          text: 'Type',
          value: 'type',
        },
        {
          text: 'Prix',
          value: 'price',
        },
        {
          text: 'Stock',
          value: 'stock',
          align: 'start',
        },
      ],
    },
  };

  public page = 1;
  public pageCount = 1;

  public data = {
    objects: [],
    priceMax: 1000,
    priceMin: 0,
  };

  public options = {
    itemsPerPage: 15,
    sortBy: [],
    sortDesc: [],
  };

  public get currentComponent(): any {
    return !this.componentName ? undefined : this.componentTableInfo[this.componentName];
  }

  public get currentEndpoint(): string {
    return !this.currentComponent ? undefined : this.currentComponent.apiEndpoint;
  }

  public get headers() {
    if (!this.currentComponent) return undefined;

    const headersClone = JSON.parse(JSON.stringify(this.currentComponent.headers));
    if (!this.mobile)
      headersClone.push({
        text: 'Actions',
        value: 'actions',
        sortable: false,
      });

    return headersClone;
  }

  public get displayName() {
    return !this.currentComponent ? undefined : this.currentComponent.displayName;
  }

  public get dataLength() {
    return this.data.objects.length;
  }

  public loading = false;

  public componentInfo = null;

  public getInfo(item) {
    this.componentInfo = item;
  }

  private isMounted = false;

  mounted() {
    this.isMounted = true;
  }

  public get mobile() {
    return this.isMounted && ['xs', 'sm'].includes(this.$vuetify.breakpoint.name);
  }

  @Watch('currentEndpoint')
  onEndpointUpdate(value: string, oldValue: string) {
    if (value != '') {
      this.data.objects = [];
      this.options = {
        itemsPerPage: 15,
        sortBy: [],
        sortDesc: [],
      };
      this.page = 1;
      this.retrieveComponents();
    }
  }

  @Watch('page')
  onPageChange(v, oldV) {
    if (v != oldV) this.retrieveComponents();
  }

  @Watch('options')
  onOptionsUpdate(value: any, oldValue: any) {
    if (value != oldValue) this.retrieveComponents();
  }

  public handleRowClick(value) {
    this.componentInfo = null;
    this.$emit('picked', value);
    this.close();
  }

  private retrieveComponents() {
    this.loading = true;
    axios
      .get(
        baseApiUrl + this.currentEndpoint,
        this.options
          ? {
              params: {
                page: this.page,
                itemsPerPage: this.options.itemsPerPage,
                sortBy: this.options.sortBy[0],
                sortDesc: this.options.sortDesc[0],
              },
            }
          : null
      )
      .then(res => {
        this.data.objects = res.data.content;
        this.pageCount = res.data.totalPages;
      })
      .catch(err => {
        console.log('Erreur lors du fetch des données');
      })
      .finally(() => {
        this.loading = false;
      });
  }

  public get show(): boolean {
    return this.activated;
  }

  public set show(v) {
    if (!v) {
      this.componentInfo = null;
      this.$emit('close');
    }
  }

  public outsideClick(e) {
    const target = e.target;
    let parent = target.parentElement;
    while (parent != null && !parent.classList.contains('component-details')) parent = parent.parentElement;

    if (parent == null) this.close();
  }

  private close() {
    this.show = false;
  }
}
