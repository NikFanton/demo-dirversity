import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserGroup } from 'app/shared/model/user-group.model';

type EntityResponseType = HttpResponse<IUserGroup>;
type EntityArrayResponseType = HttpResponse<IUserGroup[]>;

@Injectable({ providedIn: 'root' })
export class UserGroupService {
  public resourceUrl = SERVER_API_URL + 'api/user-groups';

  constructor(protected http: HttpClient) {}

  create(userGroup: IUserGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userGroup);
    return this.http
      .post<IUserGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userGroup: IUserGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userGroup);
    return this.http
      .put<IUserGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userGroup: IUserGroup): IUserGroup {
    const copy: IUserGroup = Object.assign({}, userGroup, {
      creationDate: userGroup.creationDate != null && userGroup.creationDate.isValid() ? userGroup.creationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userGroup: IUserGroup) => {
        userGroup.creationDate = userGroup.creationDate != null ? moment(userGroup.creationDate) : null;
      });
    }
    return res;
  }
}
