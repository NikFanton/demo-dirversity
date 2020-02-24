import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentModule } from 'app/shared/model/content-module.model';

type EntityResponseType = HttpResponse<IContentModule>;
type EntityArrayResponseType = HttpResponse<IContentModule[]>;

@Injectable({ providedIn: 'root' })
export class ContentModuleService {
  public resourceUrl = SERVER_API_URL + 'api/content-modules';

  constructor(protected http: HttpClient) {}

  create(contentModule: IContentModule): Observable<EntityResponseType> {
    return this.http.post<IContentModule>(this.resourceUrl, contentModule, { observe: 'response' });
  }

  update(contentModule: IContentModule): Observable<EntityResponseType> {
    return this.http.put<IContentModule>(this.resourceUrl, contentModule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentModule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
