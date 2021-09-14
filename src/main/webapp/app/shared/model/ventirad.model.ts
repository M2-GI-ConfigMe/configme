import { IDimension } from '@/shared/model/dimension.model';

export interface IVentirad {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  rangeFanSpeed?: string;
  noise?: number | null;
  hasThermalPaste?: boolean;
  dimension?: IDimension | null;
}

export class Ventirad implements IVentirad {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public rangeFanSpeed?: string,
    public noise?: number | null,
    public hasThermalPaste?: boolean,
    public dimension?: IDimension | null
  ) {
    this.hasThermalPaste = this.hasThermalPaste ?? false;
  }
}
