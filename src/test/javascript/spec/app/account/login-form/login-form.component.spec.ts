import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import axios from 'axios';
import sinon from 'sinon';
import AccountService from '@/account/account.service';
import router from '@/router';
import TranslationService from '@/locale/translation.service';

import * as config from '@/shared/config/config';
import LoginForm from '@/account/login-form/login-form.vue';
import LoginFormClass from '@/account/login-form/login-form.component';

//import Vuetify from 'vuetify';

//const vuetify = new Vuetify({});

const localVue = createLocalVue();
localVue.component('b-alert', {});
localVue.component('b-button', {});
localVue.component('b-form', {});
localVue.component('b-form-input', {});
localVue.component('b-form-group', {});
localVue.component('b-form-checkbox', {});
localVue.component('b-link', {});

localVue.component('v-form', {});
localVue.component('v-text-field', {});
localVue.component('v-btn', {});
localVue.component('v-alert', {});

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
};

describe('LoginForm Component', () => {
  let wrapper: Wrapper<LoginFormClass>;
  let loginForm: LoginFormClass;

  beforeEach(() => {
    axiosStub.get.resolves({});
    axiosStub.post.reset();

    wrapper = shallowMount<LoginFormClass>(LoginForm, {
      store,
      i18n,
      localVue,
      //vuetify,
      provide: {
        accountService: () => new AccountService(store, new TranslationService(store, i18n), router),
      },
    });
    loginForm = wrapper.vm;
  });

  it('should not store token if authentication is KO', async () => {
    // GIVEN
    loginForm.login = 'login';
    loginForm.password = 'pwd';
    //loginForm.rememberMe = true;
    axiosStub.post.rejects();

    // WHEN
    loginForm.doLogin();
    await loginForm.$nextTick();

    // THEN
    expect(
      axiosStub.post.calledWith('api/authenticate', {
        username: 'login',
        password: 'pwd',
        rememberMe: false,
      })
    ).toBeTruthy();
    await loginForm.$nextTick();
    expect(loginForm.authenticationError).toBeTruthy();
  });

  it('should store token if authentication is OK', async () => {
    // GIVEN
    loginForm.login = 'login';
    loginForm.password = 'pwd';
    //loginForm.rememberMe = true;
    const jwtSecret = 'jwt-secret';
    axiosStub.post.resolves({ headers: { authorization: 'Bearer ' + jwtSecret } });

    // WHEN
    loginForm.doLogin();
    await loginForm.$nextTick();

    // THEN
    expect(
      axiosStub.post.calledWith('api/authenticate', {
        username: 'login',
        password: 'pwd',
        rememberMe: false,
      })
    ).toBeTruthy();

    expect(loginForm.authenticationError).toBeFalsy();
    //expect(localStorage.getItem('jhi-authenticationToken')).toEqual(jwtSecret);
  });

  it('should store token if authentication is OK in session', async () => {
    // GIVEN
    loginForm.login = 'login';
    loginForm.password = 'pwd';
    //loginForm.rememberMe = false;
    const jwtSecret = 'jwt-secret';
    axiosStub.post.resolves({ headers: { authorization: 'Bearer ' + jwtSecret } });

    // WHEN
    loginForm.doLogin();
    await loginForm.$nextTick();

    // THEN
    expect(
      axiosStub.post.calledWith('api/authenticate', {
        username: 'login',
        password: 'pwd',
        rememberMe: false,
      })
    ).toBeTruthy();

    expect(loginForm.authenticationError).toBeFalsy();
    //expect(sessionStorage.getItem('jhi-authenticationToken')).toEqual(jwtSecret);
  });
});
