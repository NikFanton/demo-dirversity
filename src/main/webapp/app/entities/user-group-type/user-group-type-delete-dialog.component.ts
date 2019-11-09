import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserGroupType } from 'app/shared/model/user-group-type.model';
import { UserGroupTypeService } from './user-group-type.service';

@Component({
  selector: 'jhi-user-group-type-delete-dialog',
  templateUrl: './user-group-type-delete-dialog.component.html'
})
export class UserGroupTypeDeleteDialogComponent {
  userGroupType: IUserGroupType;

  constructor(
    protected userGroupTypeService: UserGroupTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userGroupTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userGroupTypeListModification',
        content: 'Deleted an userGroupType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-group-type-delete-popup',
  template: ''
})
export class UserGroupTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userGroupType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserGroupTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userGroupType = userGroupType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-group-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-group-type', { outlets: { popup: null } }]);
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
