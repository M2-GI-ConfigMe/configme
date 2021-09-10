import { IDimension } from '@/shared/model/dimension.model';

export interface IVentirad {
  id?: number;
  rangeFanSpeed?: string;
  noise?: number | null;
  hasThermalPaste?: boolean;
  dimension?: IDimension | null;
}

export class Ventirad implements IVentirad {
  constructor(
    public id?: number,
    public rangeFanSpeed?: string,
    public noise?: number | null,
    public hasThermalPaste?: boolean,
    public dimension?: IDimension | null
  ) {
    this.hasThermalPaste = this.hasThermalPaste ?? false;
  }
}
