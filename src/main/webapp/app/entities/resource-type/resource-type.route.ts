import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResourceType } from 'app/shared/model/resource-type.model';
import { ResourceTypeService } from './resource-type.service';
import { ResourceTypeComponent } from './resource-type.component';
import { ResourceTypeDetailComponent } from './resource-type-detail.component';
import { ResourceTypeUpdateComponent } from './resource-type-update.component';
import { ResourceTypeDeletePopupComponent } from './resource-type-delete-dialog.component';
import { IResourceType } from 'app/shared/model/resource-type.model';

@Injectable({ providedIn: 'root' })
export class ResourceTypeResolve implements Resolve<IResourceType> {
  constructor(private service: ResourceTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResourceType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ResourceType>) => response.ok),
        map((resourceType: HttpResponse<ResourceType>) => resourceType.body)
      );
    }
    return of(new ResourceType());
  }
}

export const resourceTypeRoute: Routes = [
  {
    path: '',
    component: ResourceTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.resourceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResourceTypeDetailComponent,
    resolve: {
      resourceType: ResourceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.resourceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResourceTypeUpdateComponent,
    resolve: {
      resourceType: ResourceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.resourceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResourceTypeUpdateComponent,
    resolve: {
      resourceType: ResourceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.resourceType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const resourceTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResourceTypeDeletePopupComponent,
    resolve: {
      resourceType: ResourceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.resourceType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
