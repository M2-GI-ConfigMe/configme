import AccountService from '@/account/account.service';
import UserOAuth2Service from '@/entities/user/user.oauth2.service';
import { IClientConfig } from '@/shared/model/client-config.model';
import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';

@Component
export default class ConfigMaker extends Vue {
  @Inject('userOAuth2Service') private userOAuth2Service: () => UserOAuth2Service;
  @Inject('accountService') private accountService: () => AccountService;
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

  public get selectedConfig(): IClientConfig {
    return this.selectedConfigIndex == -1 ? this.defaultConfig : this.clientConfigs[this.selectedConfigIndex];
  }

  public isFetching = false;

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
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

  public test() {
    console.log('coucou!');
  }

  public isComplete(config) {
    return config.gpu && config.cpu && config.mbe && config.psu && config.ram && config.computerCase && (config.hd1 || config.hd2);
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
