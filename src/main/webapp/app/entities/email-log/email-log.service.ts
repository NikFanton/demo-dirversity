import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmailLog } from 'app/shared/model/email-log.model';

type EntityResponseType = HttpResponse<IEmailLog>;
type EntityArrayResponseType = HttpResponse<IEmailLog[]>;

@Injectable({ providedIn: 'root' })
export class EmailLogService {
  public resourceUrl = SERVER_API_URL + 'api/email-logs';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailLog>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailLog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
