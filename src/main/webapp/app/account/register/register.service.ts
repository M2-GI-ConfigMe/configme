import axios from 'axios';

export default class RegisterService {
  public processRegistration(account: any): Promise<any> {
    account.langKey = 'fr';

    return axios.post('api/register', account);
  }

  public openRegister(instance: Vue): void {
    instance.$emit('showRegister');
  }
}
