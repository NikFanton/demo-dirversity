import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DirversitySharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [DirversitySharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class DirversityHomeModule {}
