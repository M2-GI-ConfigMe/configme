import { RamType } from '@/shared/model/enumerations/ram-type.model';
export interface IRam {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  speed?: number;
  type?: RamType;
  frequency?: number;
  unitSize?: number;
  quantity?: number;
  cas?: string;
}

export class Ram implements IRam {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public speed?: number,
    public type?: RamType,
    public frequency?: number,
    public unitSize?: number,
    public quantity?: number,
    public cas?: string
  ) {}
}
