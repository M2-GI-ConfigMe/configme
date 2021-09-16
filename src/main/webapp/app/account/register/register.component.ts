import Vue from 'vue';
import { Component, Inject, Watch } from 'vue-property-decorator';
import { email, helpers, maxLength, minLength, required, sameAs } from 'vuelidate/lib/validators';
import LoginService from '@/account/login.service';
import RegisterService from '@/account/register/register.service';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '@/constants';
import { Dictionary } from 'vue-router/types/router';

// const loginPattern = helpers.regex('alpha', /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);
// const validations: any = {
//   registerAccount: {
//     login: {
//       required,
//       minLength: minLength(1),
//       maxLength: maxLength(50),
//       pattern: loginPattern,
//     },
//     email: {
//       required,
//       minLength: minLength(5),
//       maxLength: maxLength(254),
//       email,
//     },
//     password: {
//       required,
//       minLength: minLength(4),
//       maxLength: maxLength(254),
//     },
//   },
//   confirmPassword: {
//     required,
//     minLength: minLength(4),
//     maxLength: maxLength(50),
//     // prettier-ignore
//     sameAsPassword: sameAs(function() {
//       return this.registerAccount.password;
//     }),
//   },
// };

@Component({
  //validations,
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'register-page');
    },
  },
})
export default class Register extends Vue {
  @Inject('registerService') private registerService: () => RegisterService;
  @Inject('loginService') private loginService: () => LoginService;

  public informations: any = {
    email: undefined,
    password: undefined,
    lastName: undefined,
    firstName: undefined,
    birthdate: undefined,
    streetNumber: undefined,
    streetName: undefined,
    city: undefined,
    zipCode: undefined,
  };

  //Form gestion
  public rules = {
    requiredField: [v => !!v || 'Champs obligatoire'],
    emailRules: [v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail non valide'],
    passwordRules: [
      v => !v || v.length >= 8 || 'Le mot de passe doit faire au moins 8 caractères',
      v => !v || /\d/.test(v) || 'Le mot de passe doit contenir au moins un chiffre',
    ],
    confirmPasswordRules: [v => !v || v === this.informations.password || 'Les mots de passe ne correspondent pas '],
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

  public register(): void {
    const registerAccount: any = {};

    this.error = null;
    this.errorUserExists = null;
    this.errorEmailExists = null;

    registerAccount.langKey = this.$store.getters.currentLanguage;
    registerAccount.email = this.informations.email;
    registerAccount.password = this.informations.password;
    registerAccount.lastName = this.informations.lastName;
    registerAccount.firstName = this.informations.firstName;
    registerAccount.birthdate = this.informations.birthdate;
    registerAccount.address = {
      firstName: this.informations.firstName,
      lastName: this.informations.lastName,
      streetName: this.informations.streetName,
      streetNumber: this.informations.streetNumber,
      city: this.informations.city,
      zipCode: this.informations.zipCode,
    };

    this.registerService()
      .processRegistration(registerAccount)
      .then(() => {
        this.success = true;
        this.$root.$emit('bv::hide::modal', 'register-page');
      })
      .catch(error => {
        this.success = null;
        if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.errorEmailExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      });
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  private save(date) {
    this.informations.birthdate = date;
    this.menu = false;
  }
}
