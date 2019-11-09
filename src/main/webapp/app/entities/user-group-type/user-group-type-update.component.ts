import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUserGroupType, UserGroupType } from 'app/shared/model/user-group-type.model';
import { UserGroupTypeService } from './user-group-type.service';

@Component({
  selector: 'jhi-user-group-type-update',
  templateUrl: './user-group-type-update.component.html'
})
export class UserGroupTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: []
  });

  constructor(protected userGroupTypeService: UserGroupTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userGroupType }) => {
      this.updateForm(userGroupType);
    });
  }

  updateForm(userGroupType: IUserGroupType) {
    this.editForm.patchValue({
      id: userGroupType.id,
      name: userGroupType.name,
      description: userGroupType.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userGroupType = this.createFromForm();
    if (userGroupType.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupTypeService.update(userGroupType));
    } else {
      this.subscribeToSaveResponse(this.userGroupTypeService.create(userGroupType));
    }
  }

  private createFromForm(): IUserGroupType {
    return {
      ...new UserGroupType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroupType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
