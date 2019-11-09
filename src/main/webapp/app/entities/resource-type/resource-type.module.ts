import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { ResourceTypeComponent } from './resource-type.component';
import { ResourceTypeDetailComponent } from './resource-type-detail.component';
import { ResourceTypeUpdateComponent } from './resource-type-update.component';
import { ResourceTypeDeletePopupComponent, ResourceTypeDeleteDialogComponent } from './resource-type-delete-dialog.component';
import { resourceTypeRoute, resourceTypePopupRoute } from './resource-type.route';

const ENTITY_STATES = [...resourceTypeRoute, ...resourceTypePopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResourceTypeComponent,
    ResourceTypeDetailComponent,
    ResourceTypeUpdateComponent,
    ResourceTypeDeleteDialogComponent,
    ResourceTypeDeletePopupComponent
  ],
  entryComponents: [ResourceTypeDeleteDialogComponent]
})
export class DirversityResourceTypeModule {}
