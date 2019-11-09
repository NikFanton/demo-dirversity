import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IRule } from 'app/shared/model/rule.model';

export interface IUserGroup {
  id?: number;
  name?: string;
  creationDate?: Moment;
  userGroupTypeName?: string;
  userGroupTypeId?: number;
  users?: IUser[];
  rules?: IRule[];
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public name?: string,
    public creationDate?: Moment,
    public userGroupTypeName?: string,
    public userGroupTypeId?: number,
    public users?: IUser[],
    public rules?: IRule[]
  ) {}
}
