import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Email } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailUpdateComponent } from './email-update.component';
import { EmailDeletePopupComponent } from './email-delete-dialog.component';
import { IEmail } from 'app/shared/model/email.model';

@Injectable({ providedIn: 'root' })
export class EmailResolve implements Resolve<IEmail> {
  constructor(private service: EmailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmail> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Email>) => response.ok),
        map((email: HttpResponse<Email>) => email.body)
      );
    }
    return of(new Email());
  }
}

export const emailRoute: Routes = [
  {
    path: '',
    component: EmailComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmailDetailComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmailUpdateComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmailUpdateComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmailDeletePopupComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.email.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
