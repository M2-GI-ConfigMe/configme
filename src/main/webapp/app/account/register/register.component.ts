import Vue from 'vue';
import { Component, Inject, Watch, Prop } from 'vue-property-decorator';
import { email, helpers, maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import LoginService from '@/account/login.service';
import RegisterService from '@/account/register/register.service';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '@/constants';
import { Dictionary } from 'vue-router/types/router';

import { IUser, User } from '@/shared/model/user.model';
import { Console } from 'console';

const validations: any = {
  account: {
    email: {
      required,
    },
    password: {
      required,
    },
    lastName: {
      required,
    },
    firstName: {
      required,
    },
    birthdate: {
      required,
    },
    address: {
      zipCode: {
        required,
      },
      city: {
        required,
      },
      streetName: {
        required,
      },
      streetNumber: {
        required,
      },
      firstName: {
        required,
      },
      lastName: {
        required,
      },
    },
  },
};

@Component({
  validations,
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'register-page');
    },
  },
})
export default class Register extends Vue {
  @Inject('registerService') private registerService: () => RegisterService;
  @Inject('loginService') private loginService: () => LoginService;

  public account: IUser = new User();

  @Prop({ required: true }) show: boolean;
  public get showDialog(): boolean {
    return this.show;
  }
  public set showDialog(v) {
    if (!v) this.$emit('close');
  }

  //Form gestion
  public rules = {
    requiredField: [v => !!v || 'Champ obligatoire'],
    emailRules: [v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail non valide'],
    passwordRules: [
      v => !v || v.length >= 8 || 'Le mot de passe doit faire au moins 8 caractères',
      v => !v || /\d/.test(v) || 'Le mot de passe doit contenir au moins un chiffre',
    ],
    confirmPasswordRules: [v => !v || v === this.account.password || 'Les mots de passe ne correspondent pas '],
    firstNameRules: [],
    lastNameRules: [],
    birthdateRules: [],
    streetNumberRules: [],
    streetNameRules: [],
    cityRules: [],
    zipCodeRules: [],
  };

  public confirmPassword: string = null;
  public isValid = false;

  public error = '';
  public errorEmailExists = '';
  public errorUserExists = '';
  public success = false;

  //Datepicker section
  public activePicker = null;
  public date = null;
  public menu = false;

  public showPass = false;
  public showPass2 = false;

  public loading = false;

  public register(): void {
    // const registerAccount: any = {};

    this.error = null;
    this.errorUserExists = null;
    this.errorEmailExists = null;

    this.account.address.firstName = this.account.firstName;
    this.account.address.lastName = this.account.lastName;

    this.loading = true;

    this.registerService()
      .processRegistration(this.account)
      .then(() => {
        this.success = true;
        this.showDialog = false;
        this.$root.$bvToast.toast('Inscription réussie !', {
          toaster: 'b-toaster-top-right',
          variant: 'success',
          solid: true,
        });
      })
      .catch(error => {
        this.success = null;
        this.$vuetify.goTo('#register', {});
        if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.errorEmailExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      })
      .finally(() => {
        this.loading = false;
      });
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  private save(date) {
    // this.informations.birthdate = date;
    this.$v.account.birthdate.$model = date;
    this.menu = false;
  }
}
