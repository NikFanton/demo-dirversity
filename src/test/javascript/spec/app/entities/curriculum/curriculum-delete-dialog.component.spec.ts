import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumDeleteDialogComponent } from 'app/entities/curriculum/curriculum-delete-dialog.component';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';

describe('Component Tests', () => {
  describe('Curriculum Management Delete Component', () => {
    let comp: CurriculumDeleteDialogComponent;
    let fixture: ComponentFixture<CurriculumDeleteDialogComponent>;
    let service: CurriculumService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumDeleteDialogComponent]
      })
        .overrideTemplate(CurriculumDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurriculumDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurriculumService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
