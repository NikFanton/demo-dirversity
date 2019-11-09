import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { PositionComponent } from './position.component';
import { PositionDetailComponent } from './position-detail.component';
import { PositionUpdateComponent } from './position-update.component';
import { PositionDeletePopupComponent, PositionDeleteDialogComponent } from './position-delete-dialog.component';
import { positionRoute, positionPopupRoute } from './position.route';

const ENTITY_STATES = [...positionRoute, ...positionPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PositionComponent,
    PositionDetailComponent,
    PositionUpdateComponent,
    PositionDeleteDialogComponent,
    PositionDeletePopupComponent
  ],
  entryComponents: [PositionDeleteDialogComponent]
})
export class DirversityPositionModule {}
