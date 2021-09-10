import { IClientConfig } from '@/shared/model/client-config.model';
import { IOrder } from '@/shared/model/order.model';

export interface IOrderLine {
  id?: number;
  config?: IClientConfig;
  order?: IOrder;
}

export class OrderLine implements IOrderLine {
  constructor(public id?: number, public config?: IClientConfig, public order?: IOrder) {}
}
