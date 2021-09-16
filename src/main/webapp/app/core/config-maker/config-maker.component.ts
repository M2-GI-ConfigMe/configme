import AccountService from '@/account/account.service';
import UserOAuth2Service from '@/entities/user/user.oauth2.service';
import { IClientConfig } from '@/shared/model/client-config.model';
import Component from 'vue-class-component';
import { Inject, Vue, Watch } from 'vue-property-decorator';
import ClientConfigService from '@/entities/client-config/client-config.service';
import LoginService from '@/account/login.service';

@Component({})
export default class ConfigMaker extends Vue {
  @Inject('userOAuth2Service') private userOAuth2Service: () => UserOAuth2Service;
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('clientConfigService') private clientConfigService: () => ClientConfigService;
  @Inject('loginService') private loginService: () => LoginService;

  public clientConfigs: IClientConfig[] = [];
  public selectedConfigIndex = -1;
  public defaultConfig: IClientConfig = {
    name: 'Ma config',
  };

  public configFields = [
    {
      key: 'computerCase',
      displayName: 'Boîter',
    },
    {
      key: 'mbe',
      displayName: 'mbe',
    },
    {
      key: 'cpu',
      displayName: 'cpu',
    },
    {
      key: 'ventirad',
      displayName: 'ventirad',
    },
    {
      key: 'ram',
      displayName: 'ram',
    },
    {
      key: 'gpu',
      displayName: 'gpu',
    },
    {
      key: 'hd1',
      displayName: 'Stockage 1',
    },
    {
      key: 'hd2',
      displayName: 'Stockage 2',
    },
    {
      key: 'psu',
      displayName: 'Alimentation',
    },
  ];

  public isFetching = false;
  public dialog = false;
  public formCreateValid = false;
  public formCreateConfig = {
    name: '',
  };

  public get selectedConfig(): IClientConfig {
    return this.selectedConfigIndex == -1 ? this.defaultConfig : this.clientConfigs[this.selectedConfigIndex];
  }

  public createConfig() {
    const config: IClientConfig = {
      name: this.formCreateConfig.name,
      user: this.$store.getters.account,
    };
    this.saveConfig(config);
  }

  public saveConfig(config) {
    if (config.id) {
      this.clientConfigService()
        .update(config)
        .then(res => {
          this.$root.$bvToast.toast('Config mise à jour!', {
            toaster: 'b-toaster-top-right',
            variant: 'success',
            solid: true,
            noCloseButton: true,
          });
        })
        .catch(res => {
          this.$root.$bvToast.toast('Erreur lors de la mise à jour', {
            toaster: 'b-toaster-top-right',
            variant: 'danger',
            solid: true,
            noCloseButton: true,
          });
        });
    } else {
      this.clientConfigService()
        .create(config)
        .then(res => {
          this.clientConfigs.push(res);
          this.$root.$bvToast.toast('Config créée !', {
            toaster: 'b-toaster-top-right',
            variant: 'success',
            solid: true,
            noCloseButton: true,
          });
        })
        .catch(res => {
          this.$root.$bvToast.toast('Erreur lors de la mise à jour', {
            toaster: 'b-toaster-top-right',
            variant: 'danger',
            solid: true,
            noCloseButton: true,
          });
        })
        .finally(() => {
          this.dialog = false;
        });
    }
  }

  public deleteConfig(config) {
    if (this.authenticated) {
      this.clientConfigService()
        .delete(config.id)
        .then(res => {
          this.clientConfigs.splice(this.selectedConfigIndex, 1);
          const newId = this.selectedConfigIndex > 0 ? this.selectedConfigIndex - 1 : -1;
          this.selectedConfigIndex = newId;
          this.$root.$bvToast.toast('Config supprimée !', {
            toaster: 'b-toaster-top-right',
            variant: 'success',
            solid: true,
            noCloseButton: true,
          });
        })
        .catch(res => {
          this.$root.$bvToast.toast('Erreur lors de la mise à jour', {
            toaster: 'b-toaster-top-right',
            variant: 'danger',
            solid: true,
            noCloseButton: true,
          });
        });
    } else {
      this.defaultConfig = {
        name: 'Ma config',
      };
    }
  }

  public removeComponent(key) {
    this.selectedConfig[key] = null;
  }

  public get getTotalPrice() {
    return (
      (this.selectedConfig.computerCase ? this.selectedConfig.computerCase.price : 0) +
      (this.selectedConfig.mbe ? this.selectedConfig.mbe.price : 0) +
      (this.selectedConfig.ram1 ? this.selectedConfig.ram1.price : 0) +
      (this.selectedConfig.gpu ? this.selectedConfig.gpu.price : 0) +
      (this.selectedConfig.cpu ? this.selectedConfig.cpu.price : 0) +
      (this.selectedConfig.psu ? this.selectedConfig.psu.price : 0) +
      (this.selectedConfig.deadMemory1 ? this.selectedConfig.deadMemory1.price : 0) +
      (this.selectedConfig.deadMemory2 ? this.selectedConfig.deadMemory2.price : 0)
    );
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  @Watch('authenticated')
  onAuthenticatedChanged(value: boolean, oldValue: boolean) {
    if (value) this.retrieveUserConfigs();
    else this.clientConfigs = [];
    this.selectedConfigIndex = -1;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  mounted() {
    this.accountService().retrieveAccount();
  }

  created() {
    this.retrieveUserConfigs();
  }

  sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  public isComplete(config) {
    return config.gpu && config.cpu && config.mbe && config.psu && config.ram1 && config.computerCase && (config.hd1 || config.hd2);
  }

  private retrieveUserConfigs() {
    this.isFetching = true;
    this.userOAuth2Service()
      .configs()
      .then(async res => {
        this.clientConfigs = res.data;
      })
      .finally(() => {
        this.isFetching = false;
      });
  }
}
