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
      },
      {
        path: 'curriculum',
        loadChildren: () => import('./curriculum/curriculum.module').then(m => m.DirversityCurriculumModule)
      },
      {
        path: 'content-module',
        loadChildren: () => import('./content-module/content-module.module').then(m => m.DirversityContentModuleModule)
      },
      {
        path: 'topic',
        loadChildren: () => import('./topic/topic.module').then(m => m.DirversityTopicModule)
      },
      {
        path: 'curriculum-tag',
        loadChildren: () => import('./curriculum-tag/curriculum-tag.module').then(m => m.DirversityCurriculumTagModule)
      },
      {
        path: 'email',
        loadChildren: () => import('./email/email.module').then(m => m.DirversityEmailModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class DirversityEntityModule {}
