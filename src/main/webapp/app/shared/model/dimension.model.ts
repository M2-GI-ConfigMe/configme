export interface IDimension {
  id?: number;
  height?: number;
  width?: number;
  length?: number;
}

export class Dimension implements IDimension {
  constructor(public id?: number, public height?: number, public width?: number, public length?: number) {}
}
