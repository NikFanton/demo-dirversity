import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContentModule, ContentModule } from 'app/shared/model/content-module.model';
import { ContentModuleService } from './content-module.service';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';

@Component({
  selector: 'jhi-content-module-update',
  templateUrl: './content-module-update.component.html'
})
export class ContentModuleUpdateComponent implements OnInit {
  isSaving: boolean;

  curricula: ICurriculum[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    order: [null, [Validators.required]],
    description: [],
    curriculumId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contentModuleService: ContentModuleService,
    protected curriculumService: CurriculumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contentModule }) => {
      this.updateForm(contentModule);
    });
    this.curriculumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurriculum[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurriculum[]>) => response.body)
      )
      .subscribe((res: ICurriculum[]) => (this.curricula = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contentModule: IContentModule) {
    this.editForm.patchValue({
      id: contentModule.id,
      name: contentModule.name,
      order: contentModule.order,
      description: contentModule.description,
      curriculumId: contentModule.curriculumId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contentModule = this.createFromForm();
    if (contentModule.id !== undefined) {
      this.subscribeToSaveResponse(this.contentModuleService.update(contentModule));
    } else {
      this.subscribeToSaveResponse(this.contentModuleService.create(contentModule));
    }
  }

  private createFromForm(): IContentModule {
    return {
      ...new ContentModule(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      order: this.editForm.get(['order']).value,
      description: this.editForm.get(['description']).value,
      curriculumId: this.editForm.get(['curriculumId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentModule>>) {
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
}
