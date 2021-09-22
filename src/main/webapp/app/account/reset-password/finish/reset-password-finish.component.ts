import axios from 'axios';
import { maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import { Inject, Vue, Component } from 'vue-property-decorator';
import LoginService from '@/account/login.service';

const validations = {
  resetAccount: {
    newPassword: {
      required,
      minLength: minLength(8),
      maxLength: maxLength(254),
    },
    confirmPassword: {
      // prettier-ignore
      sameAsPassword: sameAs(vm => {
      return vm.newPassword;
      }),
    },
  },
};

@Component({
  validations,
})
export default class ResetPasswordFinish extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  public rules = {
    requiredField: [v => !!v || 'Champ obligatoire'],
    passwordRules: [
      v => !v || v.length >= 8 || 'Le mot de passe doit faire au moins 8 caractères',
      v => !v || /\d/.test(v) || 'Le mot de passe doit contenir au moins un chiffre',
    ],
    confirmPasswordRules: [v => !v || v === this.resetAccount.newPassword || 'Les mots de passe ne correspondent pas '],
  };

  public doNotMatch: string = null;
  public success: string = null;
  public error: string = null;

  public isValid = false;

  public keyMissing: boolean = null;
  public key: any;

  public resetAccount: any = {
    newPassword: null,
    confirmPassword: null,
  };

  public showPass = false;
  public showPass2 = false;

  created(): void {
    if (this.$route !== undefined && this.$route.query !== undefined && this.$route.query.key !== undefined) {
      this.key = this.$route.query.key;
    }
    this.keyMissing = !this.key;
  }

  public finishReset(): void {
    this.doNotMatch = null;
    this.success = null;
    this.error = null;
    if (this.resetAccount.newPassword !== this.resetAccount.confirmPassword) {
      this.doNotMatch = 'ERROR';
    } else {
      axios
        .post('api/account/reset-password/finish', { key: this.key, newPassword: this.resetAccount.newPassword })
        .then(() => {
          this.success = 'OK';
        })
        .catch(() => {
          this.success = null;
          this.error = 'ERROR';
        });
    }
  }

  public openLogin() {
    this.loginService().openLogin((<any>this).$root);
  }
}
