import { IUser } from 'app/core/user/user.model';

export interface IPosition {
  id?: number;
  title?: string;
  titleShortForm?: string;
  description?: string;
  employees?: IUser[];
}

export class Position implements IPosition {
  constructor(
    public id?: number,
    public title?: string,
    public titleShortForm?: string,
    public description?: string,
    public employees?: IUser[]
  ) {}
}
