import { maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import axios from 'axios';
import { mapGetters } from 'vuex';
import Component from 'vue-class-component';
import { Vue } from 'vue-property-decorator';

const validations = {
  resetPassword: {
    currentPassword: {
      required,
    },
    newPassword: {
      required,
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
  computed: {
    ...mapGetters(['account']),
  },
})
export default class ChangePassword extends Vue {
  success: string = null;
  error: string = null;

  public showPass = false;
  public showPass2 = false;
  public showPass3 = false;

  public rules = {
    requiredField: [v => !!v || 'Champ obligatoire'],
    passwordRules: [
      v => !v || v.length >= 8 || 'Le nouveau mot de passe doit faire au moins 8 caractères',
      v => !v || /\d/.test(v) || 'Le nouveau mot de passe doit contenir au moins un chiffre',
    ],
    confirmPasswordRules: [v => !v || v === this.resetPassword.newPassword || 'Les nouveaux mots de passe ne correspondent pas'],
  };

  public isValid = false;

  doNotMatch: string = null;
  resetPassword: any = {
    currentPassword: null,
    newPassword: null,
    confirmPassword: null,
  };

  public changePassword(): void {
    if (this.resetPassword.newPassword !== this.resetPassword.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      axios
        .post('api/account/change-password', {
          currentPassword: this.resetPassword.currentPassword,
          newPassword: this.resetPassword.newPassword,
        })
        .then(() => {
          this.success = 'OK';
          this.error = null;
        })
        .catch(() => {
          this.success = null;
          this.error = 'ERROR';
        });
    }
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
