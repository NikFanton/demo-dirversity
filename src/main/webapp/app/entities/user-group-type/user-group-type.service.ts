import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserGroupType } from 'app/shared/model/user-group-type.model';

type EntityResponseType = HttpResponse<IUserGroupType>;
type EntityArrayResponseType = HttpResponse<IUserGroupType[]>;

@Injectable({ providedIn: 'root' })
export class UserGroupTypeService {
  public resourceUrl = SERVER_API_URL + 'api/user-group-types';

  constructor(protected http: HttpClient) {}

  create(userGroupType: IUserGroupType): Observable<EntityResponseType> {
    return this.http.post<IUserGroupType>(this.resourceUrl, userGroupType, { observe: 'response' });
  }

  update(userGroupType: IUserGroupType): Observable<EntityResponseType> {
    return this.http.put<IUserGroupType>(this.resourceUrl, userGroupType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserGroupType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserGroupType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
