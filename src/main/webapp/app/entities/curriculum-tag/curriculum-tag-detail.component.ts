import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';

@Component({
  selector: 'jhi-curriculum-tag-detail',
  templateUrl: './curriculum-tag-detail.component.html'
})
export class CurriculumTagDetailComponent implements OnInit {
  curriculumTag: ICurriculumTag;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curriculumTag }) => {
      this.curriculumTag = curriculumTag;
    });
  }

  previousState() {
    window.history.back();
  }
}
