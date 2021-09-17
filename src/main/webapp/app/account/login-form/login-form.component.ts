import axios from 'axios';
import Component from 'vue-class-component';
import { Vue, Inject, Prop } from 'vue-property-decorator';
import AccountService from '@/account/account.service';
import RegisterService from '@/account/register/register.service';

@Component({
  watch: {
    $route() {
      this.showDialog = false;
    },
  },
})
export default class LoginForm extends Vue {
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('registerService') private registerService: () => RegisterService;
  public authenticationError = null;
  public login = null;
  public password = null;
  public showPass = false;
  public loading = false;

  @Prop({ required: true }) show: boolean;
  public get showDialog(): boolean {
    return this.show;
  }
  public set showDialog(v) {
    if (!v) this.$emit('close');
  }

  //public rememberMe: boolean = null;

  public doLogin(): void {
    const data = { username: this.login, password: this.password, rememberMe: false /*this.rememberMe*/ };
    this.loading = true;
    axios
      .post('api/authenticate', data)
      .then(result => {
        const bearerToken = result.headers.authorization;
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          /*if (this.rememberMe) {
            localStorage.setItem('jhi-authenticationToken', jwt);
            sessionStorage.removeItem('jhi-authenticationToken');
          } else {*/
          sessionStorage.setItem('jhi-authenticationToken', jwt);
          localStorage.removeItem('jhi-authenticationToken');
          //}
        }
        this.authenticationError = false;
        this.showDialog = false;
        this.$root.$bvToast.toast('Connexion réussie !', {
          toaster: 'b-toaster-top-center',
          variant: 'success',
          solid: true,
          noCloseButton: false,
        });
        this.accountService().retrieveAccount();
      })
      .catch(() => {
        this.authenticationError = true;
      })
      .finally(() => {
        this.loading = false;
      });
  }

  public doRegister(): void {
    this.showDialog = false;
    this.registerService().openRegister((<any>this).$root);
  }
}
