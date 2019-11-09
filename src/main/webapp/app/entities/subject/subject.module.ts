import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { SubjectComponent } from './subject.component';
import { SubjectDetailComponent } from './subject-detail.component';
import { SubjectUpdateComponent } from './subject-update.component';
import { SubjectDeletePopupComponent, SubjectDeleteDialogComponent } from './subject-delete-dialog.component';
import { subjectRoute, subjectPopupRoute } from './subject.route';

const ENTITY_STATES = [...subjectRoute, ...subjectPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SubjectComponent,
    SubjectDetailComponent,
    SubjectUpdateComponent,
    SubjectDeleteDialogComponent,
    SubjectDeletePopupComponent
  ],
  entryComponents: [SubjectDeleteDialogComponent]
})
export class DirversitySubjectModule {}
