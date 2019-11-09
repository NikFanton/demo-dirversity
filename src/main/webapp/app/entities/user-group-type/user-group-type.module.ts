import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { UserGroupTypeComponent } from './user-group-type.component';
import { UserGroupTypeDetailComponent } from './user-group-type-detail.component';
import { UserGroupTypeUpdateComponent } from './user-group-type-update.component';
import { UserGroupTypeDeletePopupComponent, UserGroupTypeDeleteDialogComponent } from './user-group-type-delete-dialog.component';
import { userGroupTypeRoute, userGroupTypePopupRoute } from './user-group-type.route';

const ENTITY_STATES = [...userGroupTypeRoute, ...userGroupTypePopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserGroupTypeComponent,
    UserGroupTypeDetailComponent,
    UserGroupTypeUpdateComponent,
    UserGroupTypeDeleteDialogComponent,
    UserGroupTypeDeletePopupComponent
  ],
  entryComponents: [UserGroupTypeDeleteDialogComponent]
})
export class DirversityUserGroupTypeModule {}
