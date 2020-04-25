import { Moment } from 'moment';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { IRule } from 'app/shared/model/rule.model';
import { ITopic } from 'app/shared/model/topic.model';

export interface IResource {
  id?: number;
  name?: string;
  author?: string;
  accessUrl?: string;
  fileId?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  resourceTypes?: IResourceType[];
  rules?: IRule[];
  dataContentType?: string;
  data?: any;
  dataDisplayName?: string;
  topics?: ITopic[];
}

export class Resource implements IResource {
  constructor(
    public id?: number,
    public name?: string,
    public author?: string,
    public accessUrl?: string,
    public fileId?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public resourceTypes?: IResourceType[],
    public rules?: IRule[],
    public dataContentType?: string,
    public data?: any,
    public dataDisplayName?: string,
    public topics?: ITopic[]
  ) {}
}
