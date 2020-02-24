import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentModule } from 'app/shared/model/content-module.model';

@Component({
  selector: 'jhi-content-module-detail',
  templateUrl: './content-module-detail.component.html'
})
export class ContentModuleDetailComponent implements OnInit {
  contentModule: IContentModule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contentModule }) => {
      this.contentModule = contentModule;
    });
  }

  previousState() {
    window.history.back();
  }
}
