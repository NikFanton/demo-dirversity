import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUserGroup, UserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from './user-group.service';
import { IUserGroupType } from 'app/shared/model/user-group-type.model';
import { UserGroupTypeService } from 'app/entities/user-group-type/user-group-type.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';

@Component({
  selector: 'jhi-user-group-update',
  templateUrl: './user-group-update.component.html'
})
export class UserGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  usergrouptypes: IUserGroupType[];

  users: IUser[];

  rules: IRule[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    creationDate: [],
    userGroupTypeId: [],
    users: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userGroupService: UserGroupService,
    protected userGroupTypeService: UserGroupTypeService,
    protected userService: UserService,
    protected ruleService: RuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userGroup }) => {
      this.updateForm(userGroup);
    });
    this.userGroupTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserGroupType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserGroupType[]>) => response.body)
      )
      .subscribe((res: IUserGroupType[]) => (this.usergrouptypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ruleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRule[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRule[]>) => response.body)
      )
      .subscribe((res: IRule[]) => (this.rules = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userGroup: IUserGroup) {
    this.editForm.patchValue({
      id: userGroup.id,
      name: userGroup.name,
      creationDate: userGroup.creationDate != null ? userGroup.creationDate.format(DATE_TIME_FORMAT) : null,
      userGroupTypeId: userGroup.userGroupTypeId,
      users: userGroup.users
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userGroup = this.createFromForm();
    if (userGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupService.update(userGroup));
    } else {
      this.subscribeToSaveResponse(this.userGroupService.create(userGroup));
    }
  }

  private createFromForm(): IUserGroup {
    return {
      ...new UserGroup(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      userGroupTypeId: this.editForm.get(['userGroupTypeId']).value,
      users: this.editForm.get(['users']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroup>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserGroupTypeById(index: number, item: IUserGroupType) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackRuleById(index: number, item: IRule) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
