import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-group',
        loadChildren: () => import('./user-group/user-group.module').then(m => m.DirversityUserGroupModule)
      },
      {
        path: 'user-group-type',
        loadChildren: () => import('./user-group-type/user-group-type.module').then(m => m.DirversityUserGroupTypeModule)
      },
      {
        path: 'resource',
        loadChildren: () => import('./resource/resource.module').then(m => m.DirversityResourceModule)
      },
      {
        path: 'resource-type',
        loadChildren: () => import('./resource-type/resource-type.module').then(m => m.DirversityResourceTypeModule)
      },
      {
        path: 'rule',
        loadChildren: () => import('./rule/rule.module').then(m => m.DirversityRuleModule)
      },
      {
        path: 'subject',
        loadChildren: () => import('./subject/subject.module').then(m => m.DirversitySubjectModule)
      },
      {
        path: 'position',
        loadChildren: () => import('./position/position.module').then(m => m.DirversityPositionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DirversityEntityModule {}
