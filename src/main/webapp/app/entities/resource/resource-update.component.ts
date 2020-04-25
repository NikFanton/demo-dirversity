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
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IResource, Resource } from 'app/shared/model/resource.model';
import { ResourceService } from './resource.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { ResourceTypeService } from 'app/entities/resource-type/resource-type.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic/topic.service';
import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import { ContentModuleService } from 'app/entities/content-module/content-module.service';
import { IContentModule } from 'app/shared/model/content-module.model';

@Component({
  selector: 'jhi-resource-update',
  templateUrl: './resource-update.component.html'
})
export class ResourceUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  resourcetypes: IResourceType[];

  rules: IRule[];

  topics: ITopic[];

  curriculums: ICurriculum[];

  contentModules: IContentModule[];

  editForm = this.fb.group({
    id: [],
    name: [],
    author: [],
    accessUrl: [],
    fileId: [],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    resourceTypes: [],
    rules: [],
    data: [],
    dataContentType: [],
    dataDisplayName: [],
    topics: [],
    curriculums: [],
    contentModules: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected resourceService: ResourceService,
    protected userService: UserService,
    protected resourceTypeService: ResourceTypeService,
    protected ruleService: RuleService,
    protected topicService: TopicService,
    protected curriculumService: CurriculumService,
    protected contentModuleService: ContentModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resource }) => {
      this.updateForm(resource);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.resourceTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IResourceType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IResourceType[]>) => response.body)
      )
      .subscribe((res: IResourceType[]) => (this.resourcetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ruleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRule[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRule[]>) => response.body)
      )
      .subscribe((res: IRule[]) => (this.rules = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.curriculumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurriculum[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurriculum[]>) => response.body)
      )
      .subscribe((res: ICurriculum[]) => (this.curriculums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(resource: IResource) {
    this.editForm.patchValue({
      id: resource.id,
      name: resource.name,
      author: resource.author,
      accessUrl: resource.accessUrl,
      fileId: resource.fileId,
      createdBy: resource.createdBy,
      createdDate: resource.createdDate != null ? resource.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: resource.lastModifiedBy,
      lastModifiedDate: resource.lastModifiedDate != null ? resource.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      resourceTypes: resource.resourceTypes,
      rules: resource.rules,
      data: resource.data,
      dataContentType: resource.dataContentType,
      dataDisplayName: resource.dataDisplayName,
      topics: resource.topics
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  selectCurriculumOption() {
    const curriculumId = this.editForm.get(['curriculums']).value;
    if (curriculumId) {
      this.findAllContentModulesForCurriculum(curriculumId);
    }
  }

  findAllContentModulesForCurriculum(id: number) {
    this.contentModuleService
      .query({ curriculumId: id })
      .pipe(
        filter((mayBeOk: HttpResponse<IContentModule[]>) => mayBeOk.ok),
        map((response: HttpResponse<IContentModule[]>) => response.body)
      )
      .subscribe((res: IContentModule[]) => (this.contentModules = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  selectContentModuleOption() {
    const contentModulesId = this.editForm.get(['contentModules']).value;
    if (contentModulesId) {
      this.findAllTopicsForContentModule(contentModulesId);
    }
  }

  findAllTopicsForContentModule(id: number) {
    this.topicService
      .query({ contentModuleId: id })
      .pipe(
        filter((mayBeOk: HttpResponse<ITopic[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITopic[]>) => response.body)
      )
      .subscribe((res: ITopic[]) => (this.topics = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          const filedDisplayName: string = field + 'DisplayName';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type,
              [filedDisplayName]: file.name
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const resource = this.createFromForm();
    if (resource.id !== undefined) {
      this.subscribeToSaveResponse(this.resourceService.update(resource));
    } else {
      this.subscribeToSaveResponse(this.resourceService.create(resource));
    }
  }

  private createFromForm(): IResource {
    return {
      ...new Resource(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      author: this.editForm.get(['author']).value,
      accessUrl: this.editForm.get(['accessUrl']).value,
      fileId: this.editForm.get(['fileId']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      lastModifiedDate:
        this.editForm.get(['lastModifiedDate']).value != null
          ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      resourceTypes: this.editForm.get(['resourceTypes']).value,
      rules: this.editForm.get(['rules']).value,
      dataContentType: this.editForm.get(['dataContentType']).value,
      data: this.editForm.get(['data']).value,
      dataDisplayName: this.editForm.get(['dataDisplayName']).value,
      topics: this.editForm.get(['topics']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResource>>) {
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

  trackResourceTypeById(index: number, item: IResourceType) {
    return item.id;
  }

  trackRuleById(index: number, item: IRule) {
    return item.id;
  }

  trackTopicById(index: number, item: ITopic) {
    return item.id;
  }

  trackCurriculumById(index: number, item: ITopic) {
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
