export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  discount?: number | null;
  stock?: number;
  img?: string;
  desc?: string | null;
  brand?: string;
  isActive?: boolean;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number | null,
    public stock?: number,
    public img?: string,
    public desc?: string | null,
    public brand?: string,
    public isActive?: boolean
  ) {
    this.isActive = this.isActive ?? false;
  }
}
