import { MemoryType } from '@/shared/model/enumerations/memory-type.model';
export interface IHardDrive {
  id?: number;
  capacity?: number;
  speedWrite?: number;
  speedRead?: number;
  type?: MemoryType;
}

export class HardDrive implements IHardDrive {
  constructor(
    public id?: number,
    public capacity?: number,
    public speedWrite?: number,
    public speedRead?: number,
    public type?: MemoryType
  ) {}
}
