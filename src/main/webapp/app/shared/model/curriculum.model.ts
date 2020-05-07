import { IContentModule } from 'app/shared/model/content-module.model';
import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';
import { IUser } from 'app/core/user/user.model';

export interface ICurriculum {
  id?: number;
  name?: string;
  description?: string;
  explanatoryNote?: string;
  year?: number;
  totalHours?: number;
  originFileName?: string;
  originFileId?: number;
  contentModules?: IContentModule[];
  curriculumTags?: ICurriculumTag[];
  teachers?: IUser[];
}

export class Curriculum implements ICurriculum {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public explanatoryNote?: string,
    public year?: number,
    public totalHours?: number,
    public originFileName?: string,
    public originFileId?: number,
    public contentModules?: IContentModule[],
    public curriculumTags?: ICurriculumTag[],
    public teachers?: IUser[]
  ) {}
}
