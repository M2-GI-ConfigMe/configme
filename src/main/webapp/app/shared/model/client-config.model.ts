import { ICpu } from '@/shared/model/cpu.model';
import { IGpu } from '@/shared/model/gpu.model';
import { IPsu } from '@/shared/model/psu.model';
import { IVentirad } from '@/shared/model/ventirad.model';
import { IMbe } from '@/shared/model/mbe.model';
import { IComputerCase } from '@/shared/model/computer-case.model';
import { IHardDrive } from '@/shared/model/hard-drive.model';
import { IRam } from '@/shared/model/ram.model';
import { IUser } from './user.model';

export interface IClientConfig {
  id?: number;
  tags?: string | null;
  name?: string | null;
  description?: string | null;
  isComlete?: boolean | null;
  researchKey?: string | null;
  cpuPrice?: number | null;
  gpuPrice?: number | null;
  ram1Price?: number | null;
  ram2Price?: number | null;
  psuPrice?: number | null;
  computerCasePrice?: number | null;
  ventiradPrice?: number | null;
  hd1Price?: number | null;
  hd2Price?: number | null;
  cpu?: ICpu | null;
  gpu?: IGpu | null;
  psu?: IPsu | null;
  ventirad?: IVentirad | null;
  mbe?: IMbe | null;
  computerCase?: IComputerCase | null;
  deadMemory1?: IHardDrive | null;
  deadMemory2?: IHardDrive | null;
  ram1?: IRam | null;
  ram2?: IRam | null;
  user?: IUser | null;
}

export class ClientConfig implements IClientConfig {
  constructor(
    public id?: number,
    public tags?: string | null,
    public name?: string | null,
    public description?: string | null,
    public isComlete?: boolean | null,
    public researchKey?: string | null,
    public cpuPrice?: number | null,
    public gpuPrice?: number | null,
    public ram1Price?: number | null,
    public ram2Price?: number | null,
    public psuPrice?: number | null,
    public computerCasePrice?: number | null,
    public ventiradPrice?: number | null,
    public hd1Price?: number | null,
    public hd2Price?: number | null,
    public cpu?: ICpu | null,
    public gpu?: IGpu | null,
    public psu?: IPsu | null,
    public ventirad?: IVentirad | null,
    public mbe?: IMbe | null,
    public computerCase?: IComputerCase | null,
    public deadMemory1?: IHardDrive | null,
    public deadMemory2?: IHardDrive | null,
    public ram1?: IRam | null,
    public ram2?: IRam | null
  ) {
    this.isComlete = this.isComlete ?? false;
  }
}
