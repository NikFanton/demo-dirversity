import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IResource, Resource } from 'app/shared/model/resource.model';
import { ResourceService } from './resource.service';
import { ResourceComponent } from './resource.component';
import { ResourceDetailComponent } from './resource-detail.component';
import { ResourceUpdateComponent } from './resource-update.component';
import { ResourceDeletePopupComponent } from './resource-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class ResourceResolve implements Resolve<IResource> {
  constructor(private service: ResourceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResource> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Resource>) => response.ok),
        map((resource: HttpResponse<Resource>) => resource.body)
      );
    }
    return of(new Resource());
  }
}

export const resourceRoute: Routes = [
  {
    path: '',
    component: ResourceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      defaultSort: 'createdDate,desc',
      pageTitle: 'dirversityApp.resource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResourceDetailComponent,
    resolve: {
      resource: ResourceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      pageTitle: 'dirversityApp.resource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResourceUpdateComponent,
    resolve: {
      resource: ResourceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN', 'ROLE_STUDENT'],
      pageTitle: 'dirversityApp.resource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResourceUpdateComponent,
    resolve: {
      resource: ResourceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.resource.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const resourcePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResourceDeletePopupComponent,
    resolve: {
      resource: ResourceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.resource.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
