import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { EmailLogComponent } from './email-log.component';
import { EmailLogDetailComponent } from './email-log-detail.component';
import { emailLogRoute, emailLogPopupRoute } from './email-log.route';

const ENTITY_STATES = [...emailLogRoute, ...emailLogPopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EmailLogComponent, EmailLogDetailComponent],
  entryComponents: []
})
export class DirversityEmailLogModule {}
