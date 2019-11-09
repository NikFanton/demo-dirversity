import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRule } from 'app/shared/model/rule.model';

type EntityResponseType = HttpResponse<IRule>;
type EntityArrayResponseType = HttpResponse<IRule[]>;

@Injectable({ providedIn: 'root' })
export class RuleService {
  public resourceUrl = SERVER_API_URL + 'api/rules';

  constructor(protected http: HttpClient) {}

  create(rule: IRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rule);
    return this.http
      .post<IRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rule: IRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rule);
    return this.http
      .put<IRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(rule: IRule): IRule {
    const copy: IRule = Object.assign({}, rule, {
      from: rule.from != null && rule.from.isValid() ? rule.from.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.from = res.body.from != null ? moment(res.body.from) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rule: IRule) => {
        rule.from = rule.from != null ? moment(rule.from) : null;
      });
    }
    return res;
  }
}
