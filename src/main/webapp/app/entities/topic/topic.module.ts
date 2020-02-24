import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { TopicComponent } from './topic.component';
import { TopicDetailComponent } from './topic-detail.component';
import { TopicUpdateComponent } from './topic-update.component';
import { TopicDeletePopupComponent, TopicDeleteDialogComponent } from './topic-delete-dialog.component';
import { topicRoute, topicPopupRoute } from './topic.route';

const ENTITY_STATES = [...topicRoute, ...topicPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TopicComponent, TopicDetailComponent, TopicUpdateComponent, TopicDeleteDialogComponent, TopicDeletePopupComponent],
  entryComponents: [TopicDeleteDialogComponent]
})
export class DirversityTopicModule {}
