import { IUser } from 'app/core/user/user.model';

export interface ISubject {
  id?: number;
  fullName?: string;
  description?: string;
  teachers?: IUser[];
}

export class Subject implements ISubject {
  constructor(public id?: number, public fullName?: string, public description?: string, public teachers?: IUser[]) {}
}
