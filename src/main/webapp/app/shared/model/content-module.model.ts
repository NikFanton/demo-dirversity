import { ITopic } from 'app/shared/model/topic.model';

export interface IContentModule {
  id?: number;
  name?: string;
  order?: number;
  description?: string;
  topics?: ITopic[];
  curriculumName?: string;
  curriculumId?: number;
}

export class ContentModule implements IContentModule {
  constructor(
    public id?: number,
    public name?: string,
    public order?: number,
    public description?: string,
    public topics?: ITopic[],
    public curriculumName?: string,
    public curriculumId?: number
  ) {}
}
