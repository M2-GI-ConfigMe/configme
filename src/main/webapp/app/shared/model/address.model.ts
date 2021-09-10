export interface IAddress {
  id?: number;
  zipCode?: string;
  city?: string;
  streetNumber?: string;
  streetName?: string;
  complementary?: string | null;
  firstName?: string;
  lastName?: string;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public zipCode?: string,
    public city?: string,
    public streetNumber?: string,
    public streetName?: string,
    public complementary?: string | null,
    public firstName?: string,
    public lastName?: string
  ) {}
}
