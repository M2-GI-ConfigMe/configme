import { IDimension } from '@/shared/model/dimension.model';

import { BusType } from '@/shared/model/enumerations/bus-type.model';
export interface IGpu {
  id?: number;
  frequency?: number;
  memory?: number;
  consumption?: number;
  clockSpeed?: number;
  lithography?: number;
  output?: string;
  inputPower?: string;
  bus?: BusType;
  dimension?: IDimension | null;
}

export class Gpu implements IGpu {
  constructor(
    public id?: number,
    public frequency?: number,
    public memory?: number,
    public consumption?: number,
    public clockSpeed?: number,
    public lithography?: number,
    public output?: string,
    public inputPower?: string,
    public bus?: BusType,
    public dimension?: IDimension | null
  ) {}
}
