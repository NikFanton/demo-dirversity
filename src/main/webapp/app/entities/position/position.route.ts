import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Position } from 'app/shared/model/position.model';
import { PositionService } from './position.service';
import { PositionComponent } from './position.component';
import { PositionDetailComponent } from './position-detail.component';
import { PositionUpdateComponent } from './position-update.component';
import { PositionDeletePopupComponent } from './position-delete-dialog.component';
import { IPosition } from 'app/shared/model/position.model';

@Injectable({ providedIn: 'root' })
export class PositionResolve implements Resolve<IPosition> {
  constructor(private service: PositionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPosition> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Position>) => response.ok),
        map((position: HttpResponse<Position>) => position.body)
      );
    }
    return of(new Position());
  }
}

export const positionRoute: Routes = [
  {
    path: '',
    component: PositionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.position.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PositionDetailComponent,
    resolve: {
      position: PositionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.position.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PositionUpdateComponent,
    resolve: {
      position: PositionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.position.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PositionUpdateComponent,
    resolve: {
      position: PositionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.position.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const positionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PositionDeletePopupComponent,
    resolve: {
      position: PositionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.position.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
