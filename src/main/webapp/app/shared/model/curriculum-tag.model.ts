import { ICurriculum } from 'app/shared/model/curriculum.model';

export interface ICurriculumTag {
  id?: number;
  name?: string;
  curricula?: ICurriculum[];
}

export class CurriculumTag implements ICurriculumTag {
  constructor(public id?: number, public name?: string, public curricula?: ICurriculum[]) {}
}
