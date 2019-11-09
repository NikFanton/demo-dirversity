import { IResource } from 'app/shared/model/resource.model';

export interface IResourceType {
  id?: number;
  name?: string;
  description?: string;
  resources?: IResource[];
}

export class ResourceType implements IResourceType {
  constructor(public id?: number, public name?: string, public description?: string, public resources?: IResource[]) {}
}
