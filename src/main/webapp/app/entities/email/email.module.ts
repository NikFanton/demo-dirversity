import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailUpdateComponent } from './email-update.component';
import { EmailDeletePopupComponent, EmailDeleteDialogComponent } from './email-delete-dialog.component';
import { emailRoute, emailPopupRoute } from './email.route';

const ENTITY_STATES = [...emailRoute, ...emailPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EmailComponent, EmailDetailComponent, EmailUpdateComponent, EmailDeleteDialogComponent, EmailDeletePopupComponent],
  entryComponents: [EmailDeleteDialogComponent]
})
export class DirversityEmailModule {}
