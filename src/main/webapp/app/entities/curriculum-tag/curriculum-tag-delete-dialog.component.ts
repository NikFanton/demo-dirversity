import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurriculumTag } from 'app/shared/model/curriculum-tag.model';
import { CurriculumTagService } from './curriculum-tag.service';

@Component({
  selector: 'jhi-curriculum-tag-delete-dialog',
  templateUrl: './curriculum-tag-delete-dialog.component.html'
})
export class CurriculumTagDeleteDialogComponent {
  curriculumTag: ICurriculumTag;

  constructor(
    protected curriculumTagService: CurriculumTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.curriculumTagService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'curriculumTagListModification',
        content: 'Deleted an curriculumTag'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-curriculum-tag-delete-popup',
  template: ''
})
export class CurriculumTagDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curriculumTag }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CurriculumTagDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.curriculumTag = curriculumTag;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/curriculum-tag', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/curriculum-tag', { outlets: { popup: null } }]);
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
