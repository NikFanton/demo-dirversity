export interface IUserGroupType {
  id?: number;
  name?: string;
  description?: string;
}

export class UserGroupType implements IUserGroupType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
