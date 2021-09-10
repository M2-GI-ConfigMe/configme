import { SocketType } from '@/shared/model/enumerations/socket-type.model';
import { RamType } from '@/shared/model/enumerations/ram-type.model';
import { FormatType } from '@/shared/model/enumerations/format-type.model';
export interface IMbe {
  id?: number;
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
