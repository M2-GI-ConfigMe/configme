import { ModularityType } from '@/shared/model/enumerations/modularity-type.model';
export interface IPsu {
  id?: number;
  name?: string;
  price?: number;
  discount?: number;
  stock?: number;
  img?: string;
  brand?: string;
  isActive?: boolean;
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
    public name?: string,
    public price?: number,
    public discount?: number,
    public stock?: number,
    public img?: string,
    public brand?: string,
    public isActive?: boolean,
    public power?: number,
    public certification?: string | null,
    public modularity?: ModularityType,
    public nbSata?: number,
    public nbPciE?: number,
    public outputs?: string
  ) {}
}
