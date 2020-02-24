import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICurriculumTag, CurriculumTag } from 'app/shared/model/curriculum-tag.model';
import { CurriculumTagService } from './curriculum-tag.service';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';

@Component({
  selector: 'jhi-curriculum-tag-update',
  templateUrl: './curriculum-tag-update.component.html'
})
export class CurriculumTagUpdateComponent implements OnInit {
  isSaving: boolean;

  curricula: ICurriculum[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected curriculumTagService: CurriculumTagService,
    protected curriculumService: CurriculumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ curriculumTag }) => {
      this.updateForm(curriculumTag);
    });
    this.curriculumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurriculum[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurriculum[]>) => response.body)
      )
      .subscribe((res: ICurriculum[]) => (this.curricula = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(curriculumTag: ICurriculumTag) {
    this.editForm.patchValue({
      id: curriculumTag.id,
      name: curriculumTag.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const curriculumTag = this.createFromForm();
    if (curriculumTag.id !== undefined) {
      this.subscribeToSaveResponse(this.curriculumTagService.update(curriculumTag));
    } else {
      this.subscribeToSaveResponse(this.curriculumTagService.create(curriculumTag));
    }
  }

  private createFromForm(): ICurriculumTag {
    return {
      ...new CurriculumTag(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurriculumTag>>) {
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

  trackCurriculumById(index: number, item: ICurriculum) {
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
