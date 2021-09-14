import { SocketType } from '@/shared/model/enumerations/socket-type.model';
export interface ICpu {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  frequency?: number;
  cacheL1?: number | null;
  cacheL2?: number | null;
  cacheL3?: number | null;
  nbHeart?: number;
  nbThread?: number;
  hasVentirad?: boolean;
  socketType?: SocketType;
  lithography?: number;
  ramFrequencyMax?: number;
  consumption?: number;
  hasGpu?: boolean;
}

export class Cpu implements ICpu {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public frequency?: number,
    public cacheL1?: number | null,
    public cacheL2?: number | null,
    public cacheL3?: number | null,
    public nbHeart?: number,
    public nbThread?: number,
    public hasVentirad?: boolean,
    public socketType?: SocketType,
    public lithography?: number,
    public ramFrequencyMax?: number,
    public consumption?: number,
    public hasGpu?: boolean
  ) {
    this.hasVentirad = this.hasVentirad ?? false;
    this.hasGpu = this.hasGpu ?? false;
  }
}
