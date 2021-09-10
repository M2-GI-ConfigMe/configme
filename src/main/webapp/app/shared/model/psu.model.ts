import { ModularityType } from '@/shared/model/enumerations/modularity-type.model';
export interface IPsu {
  id?: number;
  power?: number;
  certification?: string | null;
  modularity?: ModularityType;
  nbSata?: number;
  nbPciE?: number;
  outputs?: string;
}

export class Psu implements IPsu {
  constructor(
    public id?: number,
    public power?: number,
    public certification?: string | null,
    public modularity?: ModularityType,
    public nbSata?: number,
    public nbPciE?: number,
    public outputs?: string
  ) {}
}
