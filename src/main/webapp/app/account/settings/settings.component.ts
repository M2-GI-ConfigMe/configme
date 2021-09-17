import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import axios from 'axios';
import { EMAIL_ALREADY_USED_TYPE } from '@/constants';
import { Vue, Inject, Component } from 'vue-property-decorator';
import AccountService from '@/account/account.service';

const validations = {
  settingsAccount: {
    email: {
      required,
      email,
    },
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    birthdate: {
      required,
    },
    address: {
      streetNumber: {
        required,
      },
      streetName: {
        required,
      },
      city: {
        required,
      },
      zipCode: {
        required,
      },
      required,
    },
  },
};

@Component({
  validations,
})
export default class Settings extends Vue {
  @Inject('accountService') private accountService: () => AccountService;

  //Form gestion
  public rules = {
    requiredField: [v => !!v || 'Champs obligatoire'],
    emailRules: [v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail non valide'],
    firstNameRules: [],
    lastNameRules: [],
    birthdateRules: [],
    streetNumberRules: [],
    streetNameRules: [],
    cityRules: [],
    zipCodeRules: [],
  };

  public isValid = false;

  public dialog = false;

  public success: string = null;
  public error: string = null;
  public errorEmailExists: string = null;
  public languages: any = this.$store.getters.languages || [];

  public save(): void {
    this.error = null;
    this.errorEmailExists = null;
    axios
      .post('api/account', this.settingsAccount)
      .then(() => {
        this.error = null;
        this.success = 'OK';
        this.errorEmailExists = null;
      })
      .catch(error => {
        this.success = null;
        this.error = 'ERROR';
        if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.errorEmailExists = 'ERROR';
          this.error = null;
        }
      });
  }

  public get settingsAccount(): any {
    return this.$store.getters.account;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public deleteAccount(): void {
    let idStocked: String = this.$store.getters.account.id;
    this.accountService().deleteUser(idStocked);
  }
}
