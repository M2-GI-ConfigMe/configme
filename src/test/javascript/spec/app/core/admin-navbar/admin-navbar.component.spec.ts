import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import AdminNavbar from '@/core/admin-navbar/admin-navbar.vue';
import AdminNavbarClass from '@/core/admin-navbar/admin-navbar.component';
import * as config from '@/shared/config/config';
import router from '@/router';

const localVue = createLocalVue();
config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-navbar', {});
localVue.component('b-navbar-nav', {});
localVue.component('b-dropdown-item', {});
localVue.component('b-collapse', {});
localVue.component('b-nav-item', {});
localVue.component('b-nav-item-dropdown', {});
localVue.component('b-navbar-toggle', {});
localVue.component('b-navbar-brand', {});
localVue.component('b-navbar-nav', {});

describe('adminNavbar', () => {
  let adminNavbar: AdminNavbarClass;
  let wrapper: Wrapper<AdminNavbarClass>;
  const loginService = { openLogin: jest.fn() };
  const accountService = { hasAnyAuthorityAndCheckAuth: jest.fn().mockImplementation(() => Promise.resolve(true)) };
  const translationService = { refreshTranslation: jest.fn() };

  beforeEach(() => {
    wrapper = shallowMount<AdminNavbarClass>(AdminNavbar, {
      i18n,
      store,
      router,
      localVue,
      provide: {
        loginService: () => loginService,
        translationService: () => translationService,
        accountService: () => accountService,
      },
    });
    adminNavbar = wrapper.vm;
  });
  it('should refresh translations', () => {
    expect(translationService.refreshTranslation).toHaveBeenCalled();
  });

  it('should not have user data set', () => {
    expect(adminNavbar.authenticated).toBeFalsy();
    expect(adminNavbar.openAPIEnabled).toBeFalsy();
    expect(adminNavbar.inProduction).toBeFalsy();
  });

  it('should have user data set after authentication', () => {
    store.commit('authenticated', { login: 'test' });

    expect(adminNavbar.authenticated).toBeTruthy();
  });

  it('should have profile info set after info retrieved', () => {
    store.commit('setActiveProfiles', ['prod', 'api-docs']);

    expect(adminNavbar.openAPIEnabled).toBeTruthy();
    expect(adminNavbar.inProduction).toBeTruthy();
  });

  it('should use login service', () => {
    adminNavbar.openLogin();
    expect(loginService.openLogin).toHaveBeenCalled();
  });

  it('should use account service', () => {
    adminNavbar.hasAnyAuthority('auth');

    expect(accountService.hasAnyAuthorityAndCheckAuth).toHaveBeenCalled();
  });

  it('logout should clear credentials', () => {
    store.commit('authenticated', { login: 'test' });
    adminNavbar.logout();

    expect(adminNavbar.authenticated).toBeFalsy();
  });

  it('should determine active route', () => {
    router.push('/toto');

    expect(adminNavbar.subIsActive('/titi')).toBeFalsy();
    expect(adminNavbar.subIsActive('/toto')).toBeTruthy();
    expect(adminNavbar.subIsActive(['/toto', 'toto'])).toBeTruthy();
  });

  it('should call translationService when changing language', () => {
    adminNavbar.changeLanguage('fr');

    expect(translationService.refreshTranslation).toHaveBeenCalled();
  });

  it('should check for correct language', () => {
    store.commit('currentLanguage', 'fr');

    expect(adminNavbar.isActiveLanguage('en')).toBeFalsy();
    expect(adminNavbar.isActiveLanguage('fr')).toBeTruthy();
  });
});
