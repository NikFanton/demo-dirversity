import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CurriculumTag } from 'app/shared/model/curriculum-tag.model';
import { CurriculumTagService } from './curriculum-tag.service';
import { CurriculumTagComponent } from './curriculum-tag.component';
import { CurriculumTagDetailComponent } from './curriculum-tag-detail.component';
import { CurriculumTagUpdateComponent } from './curriculum-tag-update.component';
import { CurriculumTagDeletePopupComponent } from './curriculum-tag-delete-dialog.component';
import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';

@Injectable({ providedIn: 'root' })
export class CurriculumTagResolve implements Resolve<ICurriculumTag> {
  constructor(private service: CurriculumTagService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICurriculumTag> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CurriculumTag>) => response.ok),
        map((curriculumTag: HttpResponse<CurriculumTag>) => curriculumTag.body)
      );
    }
    return of(new CurriculumTag());
  }
}

export const curriculumTagRoute: Routes = [
  {
    path: '',
    component: CurriculumTagComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.curriculumTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CurriculumTagDetailComponent,
    resolve: {
      curriculumTag: CurriculumTagResolve
    },
    data: {
      authorities: ['ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.curriculumTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CurriculumTagUpdateComponent,
    resolve: {
      curriculumTag: CurriculumTagResolve
    },
    data: {
      authorities: ['ROLE_TEACHER', 'ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.curriculumTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CurriculumTagUpdateComponent,
    resolve: {
      curriculumTag: CurriculumTagResolve
    },
    data: {
      authorities: ['ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.curriculumTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const curriculumTagPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CurriculumTagDeletePopupComponent,
    resolve: {
      curriculumTag: CurriculumTagResolve
    },
    data: {
      authorities: ['ROLE_CONTENT_MANAGER', 'ROLE_SYSTEM_ADMIN'],
      pageTitle: 'dirversityApp.curriculumTag.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
