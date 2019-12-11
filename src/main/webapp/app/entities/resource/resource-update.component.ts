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
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IResource, Resource } from 'app/shared/model/resource.model';
import { ResourceService } from './resource.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IResourceType } from 'app/shared/model/resource-type.model';
import { ResourceTypeService } from 'app/entities/resource-type/resource-type.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';

@Component({
  selector: 'jhi-resource-update',
  templateUrl: './resource-update.component.html'
})
export class ResourceUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  resourcetypes: IResourceType[];

  rules: IRule[];

  editForm = this.fb.group({
    id: [],
    name: [],
    author: [],
    accessUrl: [],
    createDate: [],
    publisherId: [],
    resourceTypes: [],
    rules: [],
    data: [],
    dataContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected resourceService: ResourceService,
    protected userService: UserService,
    protected resourceTypeService: ResourceTypeService,
    protected ruleService: RuleService,
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
  }

  updateForm(resource: IResource) {
    this.editForm.patchValue({
      id: resource.id,
      name: resource.name,
      author: resource.author,
      accessUrl: resource.accessUrl,
      createDate: resource.createDate != null ? resource.createDate.format(DATE_TIME_FORMAT) : null,
      publisherId: resource.publisherId,
      resourceTypes: resource.resourceTypes,
      rules: resource.rules,
      data: resource.data,
      dataContentType: resource.dataContentType
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
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
      createDate:
        this.editForm.get(['createDate']).value != null ? moment(this.editForm.get(['createDate']).value, DATE_TIME_FORMAT) : undefined,
      publisherId: this.editForm.get(['publisherId']).value,
      resourceTypes: this.editForm.get(['resourceTypes']).value,
      rules: this.editForm.get(['rules']).value,
      dataContentType: this.editForm.get(['dataContentType']).value,
      data: this.editForm.get(['data']).value
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
