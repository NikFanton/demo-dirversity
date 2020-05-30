import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailLog } from 'app/shared/model/email-log.model';

@Component({
  selector: 'jhi-email-log-detail',
  templateUrl: './email-log-detail.component.html'
})
export class EmailLogDetailComponent implements OnInit {
  emailLog: IEmailLog;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emailLog }) => {
      this.emailLog = emailLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
