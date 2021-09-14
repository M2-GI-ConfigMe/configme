import { MemoryType } from '@/shared/model/enumerations/memory-type.model';
export interface IHardDrive {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  capacity?: number;
  speedWrite?: number;
  speedRead?: number;
  type?: MemoryType;
}

export class HardDrive implements IHardDrive {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public capacity?: number,
    public speedWrite?: number,
    public speedRead?: number,
    public type?: MemoryType
  ) {}
}
