import { CaseType } from '@/shared/model/enumerations/case-type.model';
import { Dimension, IDimension } from '@/shared/model/dimension.model';
export interface IComputerCase {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
  type?: CaseType;
  formats?: string[];
  sizeMaxGpu?: number;
  sizeMaxVentirad?: number;
  sizeMaxPsu?: number;
  hardDriveSlots?: string | null;
  frontPanelOutputs?: string | null;
  fanIncluded?: string | null;
  fanSlotsAvailable?: string | null;
  watercoolingCompatibility?: string | null;
  dimension?: IDimension;
}

export class ComputerCase implements IComputerCase {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public type?: CaseType,
    public formats?: string[],
    public sizeMaxGpu?: number,
    public sizeMaxVentirad?: number,
    public sizeMaxPsu?: number,
    public hardDriveSlots?: string | null,
    public frontPanelOutputs?: string | null,
    public fanIncluded?: string | null,
    public fanSlotsAvailable?: string | null,
    public watercoolingCompatibility?: string | null,
    public dimension?: IDimension
  ) {
    this.dimension = new Dimension();
    this.formats = [];
  }
}
