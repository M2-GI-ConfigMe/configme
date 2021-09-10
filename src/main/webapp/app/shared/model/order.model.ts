import { IAddress } from '@/shared/model/address.model';

import { OrderStatus } from '@/shared/model/enumerations/order-status.model';
export interface IOrder {
  id?: number;
  createdAt?: Date;
  updatedAt?: Date;
  validatedAt?: Date;
  status?: OrderStatus;
  deliveryAddress?: IAddress | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public createdAt?: Date,
    public updatedAt?: Date,
    public validatedAt?: Date,
    public status?: OrderStatus,
    public deliveryAddress?: IAddress | null
  ) {}
}
