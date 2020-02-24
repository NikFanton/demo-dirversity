import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { ContentModuleComponent } from './content-module.component';
import { ContentModuleDetailComponent } from './content-module-detail.component';
import { ContentModuleUpdateComponent } from './content-module-update.component';
import { ContentModuleDeletePopupComponent, ContentModuleDeleteDialogComponent } from './content-module-delete-dialog.component';
import { contentModuleRoute, contentModulePopupRoute } from './content-module.route';

const ENTITY_STATES = [...contentModuleRoute, ...contentModulePopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContentModuleComponent,
    ContentModuleDetailComponent,
    ContentModuleUpdateComponent,
    ContentModuleDeleteDialogComponent,
    ContentModuleDeletePopupComponent
  ],
  entryComponents: [ContentModuleDeleteDialogComponent]
})
export class DirversityContentModuleModule {}
