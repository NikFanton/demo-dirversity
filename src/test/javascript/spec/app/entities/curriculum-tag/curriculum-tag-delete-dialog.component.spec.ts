import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumTagDeleteDialogComponent } from 'app/entities/curriculum-tag/curriculum-tag-delete-dialog.component';
import { CurriculumTagService } from 'app/entities/curriculum-tag/curriculum-tag.service';

describe('Component Tests', () => {
  describe('CurriculumTag Management Delete Component', () => {
    let comp: CurriculumTagDeleteDialogComponent;
    let fixture: ComponentFixture<CurriculumTagDeleteDialogComponent>;
    let service: CurriculumTagService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumTagDeleteDialogComponent]
      })
        .overrideTemplate(CurriculumTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurriculumTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurriculumTagService);
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
