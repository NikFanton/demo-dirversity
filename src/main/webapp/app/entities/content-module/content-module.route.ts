import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContentModule } from 'app/shared/model/content-module.model';
import { ContentModuleService } from './content-module.service';
import { ContentModuleComponent } from './content-module.component';
import { ContentModuleDetailComponent } from './content-module-detail.component';
import { ContentModuleUpdateComponent } from './content-module-update.component';
import { ContentModuleDeletePopupComponent } from './content-module-delete-dialog.component';
import { IContentModule } from 'app/shared/model/content-module.model';

@Injectable({ providedIn: 'root' })
export class ContentModuleResolve implements Resolve<IContentModule> {
  constructor(private service: ContentModuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContentModule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContentModule>) => response.ok),
        map((contentModule: HttpResponse<ContentModule>) => contentModule.body)
      );
    }
    return of(new ContentModule());
  }
}

export const contentModuleRoute: Routes = [
  {
    path: '',
    component: ContentModuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.contentModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContentModuleDetailComponent,
    resolve: {
      contentModule: ContentModuleResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.contentModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContentModuleUpdateComponent,
    resolve: {
      contentModule: ContentModuleResolve
    },
    data: {
      authorities: ['ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.contentModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContentModuleUpdateComponent,
    resolve: {
      contentModule: ContentModuleResolve
    },
    data: {
      authorities: ['ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.contentModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contentModulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContentModuleDeletePopupComponent,
    resolve: {
      contentModule: ContentModuleResolve
    },
    data: {
      authorities: ['ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.contentModule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
