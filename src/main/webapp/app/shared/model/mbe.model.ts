import { SocketType } from '@/shared/model/enumerations/socket-type.model';
import { RamType } from '@/shared/model/enumerations/ram-type.model';
import { FormatType } from '@/shared/model/enumerations/format-type.model';
export interface IMbe {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  socketCpu?: SocketType;
  ramType?: RamType;
  ramFrequencyMax?: number;
  ramSizeMax?: number;
  pciOutputs?: string;
  displayOutput?: string | null;
  storageOutput?: string | null;
  insideIO?: string | null;
  backPanelOutput?: string | null;
  bios?: string | null;
  format?: FormatType;
}

export class Mbe implements IMbe {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public socketCpu?: SocketType,
    public ramType?: RamType,
    public ramFrequencyMax?: number,
    public ramSizeMax?: number,
    public pciOutputs?: string,
    public displayOutput?: string | null,
    public storageOutput?: string | null,
    public insideIO?: string | null,
    public backPanelOutput?: string | null,
    public bios?: string | null,
    public format?: FormatType
  ) {}
}
