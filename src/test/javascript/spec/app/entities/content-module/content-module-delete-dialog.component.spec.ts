import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DirversityTestModule } from '../../../test.module';
import { ContentModuleDeleteDialogComponent } from 'app/entities/content-module/content-module-delete-dialog.component';
import { ContentModuleService } from 'app/entities/content-module/content-module.service';

describe('Component Tests', () => {
  describe('ContentModule Management Delete Component', () => {
    let comp: ContentModuleDeleteDialogComponent;
    let fixture: ComponentFixture<ContentModuleDeleteDialogComponent>;
    let service: ContentModuleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [ContentModuleDeleteDialogComponent]
      })
        .overrideTemplate(ContentModuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentModuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentModuleService);
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
