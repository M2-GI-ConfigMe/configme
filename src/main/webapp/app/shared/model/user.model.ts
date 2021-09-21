import { Address, IAddress } from './address.model';

export interface IUser {
  id?: any;
  email?: string;
  firstName?: string;
  lastName?: string;
  birthdate?: Date;
  address?: IAddress;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public email?: string,
    public firstName?: string,
    public lastName?: string,
    public birthdate?: Date,
    public address?: IAddress,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string
  ) {
    this.address = new Address();
  }
}
