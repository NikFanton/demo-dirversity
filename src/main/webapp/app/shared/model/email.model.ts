import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IResource } from 'app/shared/model/resource.model';

export interface IEmail {
  id?: number;
  body?: string;
  title?: string;
  langKey?: string;
  shareDateTime?: Moment;
  toUsers?: IUser[];
  ccUsers?: IUser[];
  toUsersGroups?: IUserGroup[];
  ccUserGroups?: IUserGroup[];
  resources?: IResource[];
}

export class Email implements IEmail {
  constructor(
    public id?: number,
    public body?: string,
    public title?: string,
    public langKey?: string,
    public shareDateTime?: Moment,
    public toUsers?: IUser[],
    public ccUsers?: IUser[],
    public toUsersGroups?: IUserGroup[],
    public ccUserGroups?: IUserGroup[],
    public resources?: IResource[]
  ) {}
}
