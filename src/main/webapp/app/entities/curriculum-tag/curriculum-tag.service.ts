import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';

type EntityResponseType = HttpResponse<ICurriculumTag>;
type EntityArrayResponseType = HttpResponse<ICurriculumTag[]>;

@Injectable({ providedIn: 'root' })
export class CurriculumTagService {
  public resourceUrl = SERVER_API_URL + 'api/curriculum-tags';

  constructor(protected http: HttpClient) {}

  create(curriculumTag: ICurriculumTag): Observable<EntityResponseType> {
    return this.http.post<ICurriculumTag>(this.resourceUrl, curriculumTag, { observe: 'response' });
  }

  update(curriculumTag: ICurriculumTag): Observable<EntityResponseType> {
    return this.http.put<ICurriculumTag>(this.resourceUrl, curriculumTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurriculumTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurriculumTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
