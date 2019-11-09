import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { RuleComponent } from './rule.component';
import { RuleDetailComponent } from './rule-detail.component';
import { RuleUpdateComponent } from './rule-update.component';
import { RuleDeletePopupComponent, RuleDeleteDialogComponent } from './rule-delete-dialog.component';
import { ruleRoute, rulePopupRoute } from './rule.route';

const ENTITY_STATES = [...ruleRoute, ...rulePopupRoute];

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RuleComponent, RuleDetailComponent, RuleUpdateComponent, RuleDeleteDialogComponent, RuleDeletePopupComponent],
  entryComponents: [RuleDeleteDialogComponent]
})
export class DirversityRuleModule {}
