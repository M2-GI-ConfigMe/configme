import Vue from 'vue';

export default class LoginService {
  public openLogin(instance: Vue): void {
    instance.$emit('showLogin');
  }
}
