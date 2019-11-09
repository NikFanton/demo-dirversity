import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { UserGroupComponent } from './user-group.component';
import { UserGroupDetailComponent } from './user-group-detail.component';
import { UserGroupUpdateComponent } from './user-group-update.component';
import { UserGroupDeletePopupComponent, UserGroupDeleteDialogComponent } from './user-group-delete-dialog.component';
import { userGroupRoute, userGroupPopupRoute } from './user-group.route';

const ENTITY_STATES = [...userGroupRoute, ...userGroupPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserGroupComponent,
    UserGroupDetailComponent,
    UserGroupUpdateComponent,
    UserGroupDeleteDialogComponent,
    UserGroupDeletePopupComponent
  ],
  entryComponents: [UserGroupDeleteDialogComponent]
})
export class DirversityUserGroupModule {}
