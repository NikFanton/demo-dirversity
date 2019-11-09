import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { ResourceComponent } from './resource.component';
import { ResourceDetailComponent } from './resource-detail.component';
import { ResourceUpdateComponent } from './resource-update.component';
import { ResourceDeletePopupComponent, ResourceDeleteDialogComponent } from './resource-delete-dialog.component';
import { resourceRoute, resourcePopupRoute } from './resource.route';

const ENTITY_STATES = [...resourceRoute, ...resourcePopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResourceComponent,
    ResourceDetailComponent,
    ResourceUpdateComponent,
    ResourceDeleteDialogComponent,
    ResourceDeletePopupComponent
  ],
  entryComponents: [ResourceDeleteDialogComponent]
})
export class DirversityResourceModule {}
