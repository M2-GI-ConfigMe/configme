import { CaseType } from '@/shared/model/enumerations/case-type.model';
export interface IComputerCase {
  id?: number;
  type?: CaseType;
  formats?: string | null;
  sizeMaxGpu?: number;
  sizeMaxVentirad?: number;
  sizeMaxPsu?: number;
  hardDriveSlots?: string | null;
  frontPanelOutputs?: string | null;
  fanIncluded?: string | null;
  fanSlotsAvailable?: string | null;
  watercoolingCompatibility?: string | null;
  dimension?: Object | null;
}

export class ComputerCase implements IComputerCase {
  constructor(
    public id?: number,
    public type?: CaseType,
    public formats?: string | null,
    public sizeMaxGpu?: number,
    public sizeMaxVentirad?: number,
    public sizeMaxPsu?: number,
    public hardDriveSlots?: string | null,
    public frontPanelOutputs?: string | null,
    public fanIncluded?: string | null,
    public fanSlotsAvailable?: string | null,
    public watercoolingCompatibility?: string | null,
    public dimension?: Object | null
  ) {}
}
