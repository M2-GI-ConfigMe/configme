import { RamType } from '@/shared/model/enumerations/ram-type.model';
export interface IRam {
  id?: number;
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
    public speed?: number,
    public type?: RamType,
    public frequency?: number,
    public unitSize?: number,
    public quantity?: number,
    public cas?: string
  ) {}
}
