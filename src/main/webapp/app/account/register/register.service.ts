import axios from 'axios';

export default class RegisterService {
  public processRegistration(account: any): Promise<any> {
    return axios.post('api/register', account);
  }

  public openRegister(instance: Vue): void {
    instance.$emit('showRegister');
  }
}
