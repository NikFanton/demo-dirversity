import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentModule } from 'app/shared/model/content-module.model';
import { ContentModuleService } from './content-module.service';

@Component({
  selector: 'jhi-content-module-delete-dialog',
  templateUrl: './content-module-delete-dialog.component.html'
})
export class ContentModuleDeleteDialogComponent {
  contentModule: IContentModule;

  constructor(
    protected contentModuleService: ContentModuleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contentModuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contentModuleListModification',
        content: 'Deleted an contentModule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-content-module-delete-popup',
  template: ''
})
export class ContentModuleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contentModule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContentModuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contentModule = contentModule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/content-module', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/content-module', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
