import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserGroupType } from 'app/shared/model/user-group-type.model';

@Component({
  selector: 'jhi-user-group-type-detail',
  templateUrl: './user-group-type-detail.component.html'
})
export class UserGroupTypeDetailComponent implements OnInit {
  userGroupType: IUserGroupType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userGroupType }) => {
      this.userGroupType = userGroupType;
    });
  }

  previousState() {
    window.history.back();
  }
}
