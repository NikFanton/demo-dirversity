import { IResource } from 'app/shared/model/resource.model';

export interface ITopic {
  id?: number;
  name?: string;
  order?: number;
  description?: string;
  contentModuleName?: string;
  contentModuleId?: number;
  resources?: IResource[];
}

export class Topic implements ITopic {
  constructor(
    public id?: number,
    public name?: string,
    public order?: number,
    public description?: string,
    public contentModuleName?: string,
    public contentModuleId?: number,
    public resources?: IResource[]
  ) {}
}
