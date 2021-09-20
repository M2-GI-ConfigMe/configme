import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';
import axios from 'axios';

const baseApiUrl = 'api/';

@Component({})
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
          align: 'start',
          value: 'name',
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
      ],
    },
  };

  public data = {
    objects: [],
    priceMax: 1000,
    priceMin: 0,
  };

  public get currentComponent(): any {
    return !this.componentName ? undefined : this.componentTableInfo[this.componentName];
  }

  public get currentEndpoint(): string {
    return !this.currentComponent ? undefined : this.currentComponent.apiEndpoint;
  }

  public get headers() {
    return !this.currentComponent ? undefined : this.currentComponent.headers;
  }

  public get displayName() {
    return !this.currentComponent ? undefined : this.currentComponent.displayName;
  }

  public get dataLength() {
    return this.data.objects.length;
  }

  public options = {};
  public loading = false;

  @Watch('currentEndpoint')
  onAuthenticatedChanged(value: string, oldValue: string) {
    if (value != '') {
      this.data.objects = [];
      this.retrieveComponents();
    }
  }

  public handleRowClick(value) {
    this.$emit('picked', value);
  }

  private retrieveComponents() {
    this.loading = true;
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + this.currentEndpoint)
        .then(res => {
          this.data.objects = res.data;
        })
        .catch(err => {
          console.log('Erreur lors du fetch des données');
        })
        .finally(() => {
          this.loading = false;
        });
    });
  }

  public get show(): boolean {
    return this.activated;
  }

  public set show(v) {
    this.$emit('close');
  }
}
