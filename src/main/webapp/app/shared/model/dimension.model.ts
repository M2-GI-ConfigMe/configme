export interface IDimension {
  height?: number;
  width?: number;
  length?: number;
}

export class Dimension implements IDimension {
  constructor(public height?: number, public width?: number, public length?: number) {}
}
