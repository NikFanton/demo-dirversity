import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmail } from 'app/shared/model/email.model';

type EntityResponseType = HttpResponse<IEmail>;
type EntityArrayResponseType = HttpResponse<IEmail[]>;

@Injectable({ providedIn: 'root' })
export class EmailService {
  public resourceUrl = SERVER_API_URL + 'api/emails';

  constructor(protected http: HttpClient) {}

  create(email: IEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(email);
    return this.http
      .post<IEmail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  sendEmail(id: number): Observable<EntityResponseType> {
    return this.http.post<IEmail>(`${this.resourceUrl}/${id}/send`, null, { observe: 'response' });
  }

  update(email: IEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(email);
    return this.http
      .put<IEmail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(email: IEmail): IEmail {
    const copy: IEmail = Object.assign({}, email, {
      shareDateTime: email.shareDateTime != null && email.shareDateTime.isValid() ? email.shareDateTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.shareDateTime = res.body.shareDateTime != null ? moment(res.body.shareDateTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((email: IEmail) => {
        email.shareDateTime = email.shareDateTime != null ? moment(email.shareDateTime) : null;
      });
    }
    return res;
  }
}
