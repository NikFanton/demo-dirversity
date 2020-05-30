import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmailLog, IEmailLog } from 'app/shared/model/email-log.model';
import { EmailLogService } from './email-log.service';
import { EmailLogComponent } from './email-log.component';
import { EmailLogDetailComponent } from './email-log-detail.component';

@Injectable({ providedIn: 'root' })
export class EmailLogResolve implements Resolve<IEmailLog> {
  constructor(private service: EmailLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmailLog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmailLog>) => response.ok),
        map((emailLog: HttpResponse<EmailLog>) => emailLog.body)
      );
    }
    return of(new EmailLog());
  }
}

export const emailLogRoute: Routes = [
  {
    path: '',
    component: EmailLogComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.emailLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmailLogDetailComponent,
    resolve: {
      emailLog: EmailLogResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.emailLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailLogPopupRoute: Routes = [];
