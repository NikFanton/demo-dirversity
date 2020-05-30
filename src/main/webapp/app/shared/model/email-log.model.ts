import { IResource } from 'app/shared/model/resource.model';
import { Moment } from 'moment';

export interface IEmailLog {
  id?: number;
  createdDate?: Moment;
  logMessage?: string;
  emailId?: number;
  sharedResources?: IResource[];
}

export class EmailLog implements IEmailLog {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public logMessage?: string,
    public emailId?: number,
    public sharedResources?: IResource[]
  ) {}
}
