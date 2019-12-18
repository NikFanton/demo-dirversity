import { Moment } from 'moment';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { IRule } from 'app/shared/model/rule.model';

export interface IResource {
  id?: number;
  name?: string;
  author?: string;
  accessUrl?: string;
  createDate?: Moment;
  fileId?: string;
  publisherLastName?: string;
  publisherId?: number;
  resourceTypes?: IResourceType[];
  rules?: IRule[];
  dataContentType?: string;
  data?: any;
  dataDisplayName?: string;
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public name?: string,
    public author?: string,
    public accessUrl?: string,
    public createDate?: Moment,
    public fileId?: string,
    public publisherLastName?: string,
    public publisherId?: number,
    public resourceTypes?: IResourceType[],
    public rules?: IRule[],
    public dataContentType?: string,
    public data?: any,
    public dataDisplayName?: string
  ) {}
}
