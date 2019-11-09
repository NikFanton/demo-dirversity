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
import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource/resource.service';

@Component({
  selector: 'jhi-rule-update',
  templateUrl: './rule-update.component.html'
})
export class RuleUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  usergroups: IUserGroup[];

  resources: IResource[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    accessType: [],
    from: [],
    users: [],
    userGroups: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ruleService: RuleService,
    protected userService: UserService,
    protected userGroupService: UserGroupService,
    protected resourceService: ResourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rule }) => {
      this.updateForm(rule);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserGroup[]>) => response.body)
      )
      .subscribe((res: IUserGroup[]) => (this.usergroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.resourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResource[]>) => response.body)
      )
      .subscribe((res: IResource[]) => (this.resources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rule: IRule) {
    this.editForm.patchValue({
      id: rule.id,
      name: rule.name,
      accessType: rule.accessType,
      from: rule.from != null ? rule.from.format(DATE_TIME_FORMAT) : null,
      users: rule.users,
      userGroups: rule.userGroups
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rule = this.createFromForm();
    if (rule.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleService.update(rule));
    } else {
      this.subscribeToSaveResponse(this.ruleService.create(rule));
    }
  }

  private createFromForm(): IRule {
    return {
      ...new Rule(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      accessType: this.editForm.get(['accessType']).value,
      from: this.editForm.get(['from']).value != null ? moment(this.editForm.get(['from']).value, DATE_TIME_FORMAT) : undefined,
      users: this.editForm.get(['users']).value,
      userGroups: this.editForm.get(['userGroups']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackUserGroupById(index: number, item: IUserGroup) {
    return item.id;
  }

  trackResourceById(index: number, item: IResource) {
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
