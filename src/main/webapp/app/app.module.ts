import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DirversitySharedModule } from 'app/shared/shared.module';
import { DirversityCoreModule } from 'app/core/core.module';
import { DirversityAppRoutingModule } from './app-routing.module';
import { DirversityHomeModule } from './home/home.module';
import { DirversityEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DirversitySharedModule,
    DirversityCoreModule,
    DirversityHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DirversityEntityModule,
    DirversityAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class DirversityAppModule {}
