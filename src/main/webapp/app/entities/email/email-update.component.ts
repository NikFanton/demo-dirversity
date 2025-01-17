import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { Email, IEmail } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource/resource.service';
import { JhiLanguageHelper } from 'app/core/language/language.helper';

@Component({
  selector: 'jhi-email-update',
  templateUrl: './email-update.component.html'
})
export class EmailUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  usergroups: IUserGroup[];

  resources: IResource[];

  languages: any[];

  editForm = this.fb.group({
    id: [],
    body: [],
    title: [],
    langKey: [],
    shareDateTime: [],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    toUsers: [],
    ccUsers: [],
    toUsersGroups: [],
    ccUserGroups: [],
    resources: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected emailService: EmailService,
    protected userService: UserService,
    protected userGroupService: UserGroupService,
    protected resourceService: ResourceService,
    protected activatedRoute: ActivatedRoute,
    protected languageHelper: JhiLanguageHelper,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ email }) => {
      this.updateForm(email);
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
    this.languages = this.languageHelper.getAll();
  }

  updateForm(email: IEmail) {
    this.editForm.patchValue({
      id: email.id,
      body: email.body,
      title: email.title,
      langKey: email.langKey,
      shareDateTime: email.shareDateTime != null ? email.shareDateTime.format(DATE_TIME_FORMAT) : null,
      createdBy: email.createdBy,
      createdDate: email.createdDate != null ? email.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: email.lastModifiedBy,
      lastModifiedDate: email.lastModifiedDate != null ? email.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      toUsers: email.toUsers,
      ccUsers: email.ccUsers,
      toUsersGroups: email.toUsersGroups,
      ccUserGroups: email.ccUserGroups,
      resources: email.resources
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const email = this.createFromForm();
    if (email.id !== undefined) {
      this.subscribeToSaveResponse(this.emailService.update(email));
    } else {
      this.subscribeToSaveResponse(this.emailService.create(email));
    }
  }

  private createFromForm(): IEmail {
    return {
      ...new Email(),
      id: this.editForm.get(['id']).value,
      body: this.editForm.get(['body']).value,
      title: this.editForm.get(['title']).value,
      langKey: this.editForm.get(['langKey']).value,
      shareDateTime:
        this.editForm.get(['shareDateTime']).value != null
          ? moment(this.editForm.get(['shareDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      lastModifiedDate:
        this.editForm.get(['lastModifiedDate']).value != null
          ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      toUsers: this.editForm.get(['toUsers']).value,
      ccUsers: this.editForm.get(['ccUsers']).value,
      toUsersGroups: this.editForm.get(['toUsersGroups']).value,
      ccUserGroups: this.editForm.get(['ccUserGroups']).value,
      resources: this.editForm.get(['resources']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>) {
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
