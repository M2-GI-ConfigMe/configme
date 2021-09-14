import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';

@Component
export default class Navbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;
  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValue = false;

  public appTitle = 'ConfigMe';
  public sidebar = false;
  public entitiesItems = [
    { title: 'Cpus', path: '/cpu' },
    { title: 'Mbes', path: '/mbe' },
    { title: 'Psus', path: '/psu' },
    { title: 'Gpus', path: '/gpu' },
    { title: 'Rams', path: '/ram' },
    { title: 'Hard Drives', path: '/hard-drive' },
    { title: 'Computer Cases', path: '/computer-case' },
    { title: 'Ventirad', path: '/ventirad' },
    { title: 'Orders', path: '/order' },
  ];

  public adminItems = [
    { title: 'User Management', path: '/admin/user-management' },
    { title: 'Metrics', path: '/admin/metrics' },
    { title: 'Health', path: '/admin/health' },
    { title: 'Logs', path: '/admin/logs' },
    { title: 'Configuration', path: '/admin/configuration' },
    { title: 'Docs', path: '/admin/docs' },
  ];

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
  }

  public hasItemActive(menu) {
    let active = false;
    menu.forEach(element => {
      active = active || this.$route.path == element.path;
    });
    return active;
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    this.$router.push('/', () => {});
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get openAPIEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('api-docs') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }
}
