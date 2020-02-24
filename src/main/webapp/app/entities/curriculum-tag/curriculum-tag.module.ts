import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { CurriculumTagComponent } from './curriculum-tag.component';
import { CurriculumTagDetailComponent } from './curriculum-tag-detail.component';
import { CurriculumTagUpdateComponent } from './curriculum-tag-update.component';
import { CurriculumTagDeletePopupComponent, CurriculumTagDeleteDialogComponent } from './curriculum-tag-delete-dialog.component';
import { curriculumTagRoute, curriculumTagPopupRoute } from './curriculum-tag.route';

const ENTITY_STATES = [...curriculumTagRoute, ...curriculumTagPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurriculumTagComponent,
    CurriculumTagDetailComponent,
    CurriculumTagUpdateComponent,
    CurriculumTagDeleteDialogComponent,
    CurriculumTagDeletePopupComponent
  ],
  entryComponents: [CurriculumTagDeleteDialogComponent]
})
export class DirversityCurriculumTagModule {}
