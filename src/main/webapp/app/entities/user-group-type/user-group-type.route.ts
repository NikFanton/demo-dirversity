import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserGroupType } from 'app/shared/model/user-group-type.model';
import { UserGroupTypeService } from './user-group-type.service';
import { UserGroupTypeComponent } from './user-group-type.component';
import { UserGroupTypeDetailComponent } from './user-group-type-detail.component';
import { UserGroupTypeUpdateComponent } from './user-group-type-update.component';
import { UserGroupTypeDeletePopupComponent } from './user-group-type-delete-dialog.component';
import { IUserGroupType } from 'app/shared/model/user-group-type.model';

@Injectable({ providedIn: 'root' })
export class UserGroupTypeResolve implements Resolve<IUserGroupType> {
  constructor(private service: UserGroupTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserGroupType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserGroupType>) => response.ok),
        map((userGroupType: HttpResponse<UserGroupType>) => userGroupType.body)
      );
    }
    return of(new UserGroupType());
  }
}

export const userGroupTypeRoute: Routes = [
  {
    path: '',
    component: UserGroupTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.userGroupType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserGroupTypeDetailComponent,
    resolve: {
      userGroupType: UserGroupTypeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      pageTitle: 'dirversityApp.userGroupType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserGroupTypeUpdateComponent,
    resolve: {
      userGroupType: UserGroupTypeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.userGroupType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserGroupTypeUpdateComponent,
    resolve: {
      userGroupType: UserGroupTypeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.userGroupType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userGroupTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserGroupTypeDeletePopupComponent,
    resolve: {
      userGroupType: UserGroupTypeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.userGroupType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
