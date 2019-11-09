import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IResource } from 'app/shared/model/resource.model';
import { AccessType } from 'app/shared/model/enumerations/access-type.model';

export interface IRule {
  id?: number;
  name?: string;
  accessType?: AccessType;
  from?: Moment;
  users?: IUser[];
  userGroups?: IUserGroup[];
  resources?: IResource[];
}

export class Rule implements IRule {
  constructor(
    public id?: number,
    public name?: string,
    public accessType?: AccessType,
    public from?: Moment,
    public users?: IUser[],
    public userGroups?: IUserGroup[],
    public resources?: IResource[]
  ) {}
}
