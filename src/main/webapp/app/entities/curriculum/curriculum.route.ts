import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Curriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from './curriculum.service';
import { CurriculumComponent } from './curriculum.component';
import { CurriculumDetailComponent } from './curriculum-detail.component';
import { CurriculumUpdateComponent } from './curriculum-update.component';
import { CurriculumDeletePopupComponent } from './curriculum-delete-dialog.component';
import { ICurriculum } from 'app/shared/model/curriculum.model';

@Injectable({ providedIn: 'root' })
export class CurriculumResolve implements Resolve<ICurriculum> {
  constructor(private service: CurriculumService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICurriculum> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Curriculum>) => response.ok),
        map((curriculum: HttpResponse<Curriculum>) => curriculum.body)
      );
    }
    return of(new Curriculum());
  }
}

export const curriculumRoute: Routes = [
  {
    path: '',
    component: CurriculumComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.curriculum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CurriculumDetailComponent,
    resolve: {
      curriculum: CurriculumResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER'],
      pageTitle: 'dirversityApp.curriculum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CurriculumUpdateComponent,
    resolve: {
      curriculum: CurriculumResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER'],
      pageTitle: 'dirversityApp.curriculum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CurriculumUpdateComponent,
    resolve: {
      curriculum: CurriculumResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER'],
      pageTitle: 'dirversityApp.curriculum.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const curriculumPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CurriculumDeletePopupComponent,
    resolve: {
      curriculum: CurriculumResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dirversityApp.curriculum.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
