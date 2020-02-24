import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurriculum } from 'app/shared/model/curriculum.model';

@Component({
  selector: 'jhi-curriculum-detail',
  templateUrl: './curriculum-detail.component.html'
})
export class CurriculumDetailComponent implements OnInit {
  curriculum: ICurriculum;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curriculum }) => {
      this.curriculum = curriculum;
    });
  }

  previousState() {
    window.history.back();
  }
}
