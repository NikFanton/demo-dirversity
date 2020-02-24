import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITopic, Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { IContentModule } from 'app/shared/model/content-module.model';
import { ContentModuleService } from 'app/entities/content-module/content-module.service';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource/resource.service';

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.component.html'
})
export class TopicUpdateComponent implements OnInit {
  isSaving: boolean;

  contentmodules: IContentModule[];

  resources: IResource[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    order: [null, [Validators.required]],
    description: [],
    contentModuleId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected topicService: TopicService,
    protected contentModuleService: ContentModuleService,
    protected resourceService: ResourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.updateForm(topic);
    });
    this.contentModuleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IContentModule[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContentModule[]>) => response.body)
      )
      .subscribe((res: IContentModule[]) => (this.contentmodules = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.resourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResource[]>) => response.body)
      )
      .subscribe((res: IResource[]) => (this.resources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(topic: ITopic) {
    this.editForm.patchValue({
      id: topic.id,
      name: topic.name,
      order: topic.order,
      description: topic.description,
      contentModuleId: topic.contentModuleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const topic = this.createFromForm();
    if (topic.id !== undefined) {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    }
  }

  private createFromForm(): ITopic {
    return {
      ...new Topic(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      order: this.editForm.get(['order']).value,
      description: this.editForm.get(['description']).value,
      contentModuleId: this.editForm.get(['contentModuleId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>) {
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

  trackContentModuleById(index: number, item: IContentModule) {
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
