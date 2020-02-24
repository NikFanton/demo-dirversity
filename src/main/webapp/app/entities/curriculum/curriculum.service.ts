import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurriculum } from 'app/shared/model/curriculum.model';

type EntityResponseType = HttpResponse<ICurriculum>;
type EntityArrayResponseType = HttpResponse<ICurriculum[]>;

@Injectable({ providedIn: 'root' })
export class CurriculumService {
  public resourceUrl = SERVER_API_URL + 'api/curricula';

  constructor(protected http: HttpClient) {}

  create(curriculum: ICurriculum): Observable<EntityResponseType> {
    return this.http.post<ICurriculum>(this.resourceUrl, curriculum, { observe: 'response' });
  }

  update(curriculum: ICurriculum): Observable<EntityResponseType> {
    return this.http.put<ICurriculum>(this.resourceUrl, curriculum, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurriculum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurriculum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
