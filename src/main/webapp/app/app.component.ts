import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import AdminNavbar from '@/core/admin-navbar/admin-navbar.vue';
import Navbar from '@/core/navbar/navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';

import Register from '@/account/register/register.vue';

import '@/shared/config/dayjs';

@Component({
  components: {
    ribbon: Ribbon,
    'admin-navbar': AdminNavbar,
    navbar: Navbar,
    'login-form': LoginForm,
    'register-form': Register,

    'jhi-footer': JhiFooter,
  },
})
export default class App extends Vue {
  public showLogin = false;
  public showRegister = false;
  created() {
    this.$root.$on('showLogin', this.showLoginEventHandler);
    this.$root.$on('showRegister', this.showRegisterEventHandler);
  }

  showLoginEventHandler() {
    this.showLogin = true;
  }

  showRegisterEventHandler() {
    this.showRegister = true;
  }
}
