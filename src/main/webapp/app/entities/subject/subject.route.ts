import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Subject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';
import { SubjectComponent } from './subject.component';
import { SubjectDetailComponent } from './subject-detail.component';
import { SubjectUpdateComponent } from './subject-update.component';
import { SubjectDeletePopupComponent } from './subject-delete-dialog.component';
import { ISubject } from 'app/shared/model/subject.model';

@Injectable({ providedIn: 'root' })
export class SubjectResolve implements Resolve<ISubject> {
  constructor(private service: SubjectService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubject> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Subject>) => response.ok),
        map((subject: HttpResponse<Subject>) => subject.body)
      );
    }
    return of(new Subject());
  }
}

export const subjectRoute: Routes = [
  {
    path: '',
    component: SubjectComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.subject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubjectDetailComponent,
    resolve: {
      subject: SubjectResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      pageTitle: 'dirversityApp.subject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubjectUpdateComponent,
    resolve: {
      subject: SubjectResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.subject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubjectUpdateComponent,
    resolve: {
      subject: SubjectResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.subject.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subjectPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubjectDeletePopupComponent,
    resolve: {
      subject: SubjectResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.subject.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
