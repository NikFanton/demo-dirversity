import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICurriculum, Curriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from './curriculum.service';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource/resource.service';
import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';
import { CurriculumTagService } from 'app/entities/curriculum-tag/curriculum-tag.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-curriculum-update',
  templateUrl: './curriculum-update.component.html'
})
export class CurriculumUpdateComponent implements OnInit {
  isSaving: boolean;

  originfiles: IResource[];

  curriculumtags: ICurriculumTag[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    explanatoryNote: [],
    year: [null, [Validators.min(0)]],
    credits: [null, [Validators.min(0)]],
    originFileId: [],
    curriculumTags: [],
    teachers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected curriculumService: CurriculumService,
    protected resourceService: ResourceService,
    protected curriculumTagService: CurriculumTagService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ curriculum }) => {
      this.updateForm(curriculum);
    });
    this.resourceService
      .query({ filter: 'curriculum-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResource[]>) => response.body)
      )
      .subscribe(
        (res: IResource[]) => {
          if (!this.editForm.get('originFileId').value) {
            this.originfiles = res;
          } else {
            this.resourceService
              .find(this.editForm.get('originFileId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IResource>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IResource>) => subResponse.body)
              )
              .subscribe(
                (subRes: IResource) => (this.originfiles = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.curriculumTagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurriculumTag[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurriculumTag[]>) => response.body)
      )
      .subscribe((res: ICurriculumTag[]) => (this.curriculumtags = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(curriculum: ICurriculum) {
    this.editForm.patchValue({
      id: curriculum.id,
      name: curriculum.name,
      description: curriculum.description,
      explanatoryNote: curriculum.explanatoryNote,
      year: curriculum.year,
      credits: curriculum.credits,
      originFileId: curriculum.originFileId,
      curriculumTags: curriculum.curriculumTags,
      teachers: curriculum.teachers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const curriculum = this.createFromForm();
    if (curriculum.id !== undefined) {
      this.subscribeToSaveResponse(this.curriculumService.update(curriculum));
    } else {
      this.subscribeToSaveResponse(this.curriculumService.create(curriculum));
    }
  }

  private createFromForm(): ICurriculum {
    return {
      ...new Curriculum(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      explanatoryNote: this.editForm.get(['explanatoryNote']).value,
      year: this.editForm.get(['year']).value,
      credits: this.editForm.get(['credits']).value,
      originFileId: this.editForm.get(['originFileId']).value,
      curriculumTags: this.editForm.get(['curriculumTags']).value,
      teachers: this.editForm.get(['teachers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurriculum>>) {
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

  trackResourceById(index: number, item: IResource) {
    return item.id;
  }

  trackCurriculumTagById(index: number, item: ICurriculumTag) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
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
