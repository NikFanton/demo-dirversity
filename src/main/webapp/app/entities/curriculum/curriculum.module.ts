import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { CurriculumComponent } from './curriculum.component';
import { CurriculumDetailComponent } from './curriculum-detail.component';
import { CurriculumUpdateComponent } from './curriculum-update.component';
import { CurriculumDeletePopupComponent, CurriculumDeleteDialogComponent } from './curriculum-delete-dialog.component';
import { curriculumRoute, curriculumPopupRoute } from './curriculum.route';

const ENTITY_STATES = [...curriculumRoute, ...curriculumPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurriculumComponent,
    CurriculumDetailComponent,
    CurriculumUpdateComponent,
    CurriculumDeleteDialogComponent,
    CurriculumDeletePopupComponent
  ],
  entryComponents: [CurriculumDeleteDialogComponent]
})
export class DirversityCurriculumModule {}
