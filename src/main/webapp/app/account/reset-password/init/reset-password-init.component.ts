import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import axios from 'axios';
import { Vue, Component } from 'vue-property-decorator';

const validations = {
  resetAccount: {
    email: {
      required,
      minLength: minLength(5),
      maxLength: maxLength(254),
      email,
    },
  },
};

interface ResetAccount {
  email: string;
}

@Component({
  validations,
})
export default class ResetPasswordInit extends Vue {
  public success: boolean = null;
  public error: string = null;

  public isValid = false;

  public resetAccount: ResetAccount = {
    email: null,
  };

  public rules = {
    requiredField: [v => !!v || 'Champ obligatoire'],
    emailRules: [v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail non valide'],
  };

  public requestReset(): void {
    this.error = null;
    axios
      .post('api/account/reset-password/init', this.resetAccount.email, {
        headers: {
          'content-type': 'text/plain',
        },
      })
      .then(() => {
        this.success = true;
      })
      .catch(error => {
        this.success = null;
        this.error = 'ERROR';
      });
  }
}
