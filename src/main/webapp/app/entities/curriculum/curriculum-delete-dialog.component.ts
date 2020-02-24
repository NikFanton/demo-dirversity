import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurriculum } from 'app/shared/model/curriculum.model';
import { CurriculumService } from './curriculum.service';

@Component({
  selector: 'jhi-curriculum-delete-dialog',
  templateUrl: './curriculum-delete-dialog.component.html'
})
export class CurriculumDeleteDialogComponent {
  curriculum: ICurriculum;

  constructor(
    protected curriculumService: CurriculumService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.curriculumService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'curriculumListModification',
        content: 'Deleted an curriculum'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-curriculum-delete-popup',
  template: ''
})
export class CurriculumDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curriculum }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CurriculumDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.curriculum = curriculum;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/curriculum', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/curriculum', { outlets: { popup: null } }]);
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
