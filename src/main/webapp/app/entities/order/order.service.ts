import axios from 'axios';

import { IOrder } from '@/shared/model/order.model';
import { rejects } from 'assert';

const baseApiUrl = 'api/orders';

export default class OrderService {
  public find(id: number): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(cart: any): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, cart)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public pay(entity: IOrder): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/${entity.id}/pay`)
        .then(res => {
          resolve(res.data);
        })
        .catch(error => {
          reject(error);
        });
    });
  }

  public update(entity: IOrder): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IOrder): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${entity.id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public cart(): Promise<IOrder> {
    return new Promise<IOrder>((resolve, reject) => {
      axios
        .get(`/api/order/cart`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
