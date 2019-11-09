import { Moment } from 'moment';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { IRule } from 'app/shared/model/rule.model';

export interface IResource {
  id?: number;
  name?: string;
  author?: string;
  accessUrl?: string;
  createDate?: Moment;
  publisherLastName?: string;
  publisherId?: number;
  resourceTypes?: IResourceType[];
  rules?: IRule[];
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public name?: string,
    public author?: string,
    public accessUrl?: string,
    public createDate?: Moment,
    public publisherLastName?: string,
    public publisherId?: number,
    public resourceTypes?: IResourceType[],
    public rules?: IRule[]
  ) {}
}
