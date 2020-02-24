import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { RuleComponent } from './rule.component';
import { RuleDetailComponent } from './rule-detail.component';
import { RuleUpdateComponent } from './rule-update.component';
import { RuleDeletePopupComponent } from './rule-delete-dialog.component';
import { IRule } from 'app/shared/model/rule.model';

@Injectable({ providedIn: 'root' })
export class RuleResolve implements Resolve<IRule> {
  constructor(private service: RuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Rule>) => response.ok),
        map((rule: HttpResponse<Rule>) => rule.body)
      );
    }
    return of(new Rule());
  }
}

export const ruleRoute: Routes = [
  {
    path: '',
    component: RuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      defaultSort: 'id,asc',
      pageTitle: 'dirversityApp.rule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RuleDetailComponent,
    resolve: {
      rule: RuleResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.rule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RuleUpdateComponent,
    resolve: {
      rule: RuleResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.rule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RuleUpdateComponent,
    resolve: {
      rule: RuleResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.rule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RuleDeletePopupComponent,
    resolve: {
      rule: RuleResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_TEACHER', 'ROLE_CONTENT_MANAGER'],
      pageTitle: 'dirversityApp.rule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
