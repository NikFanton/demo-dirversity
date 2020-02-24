import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { ContentModuleUpdateComponent } from 'app/entities/content-module/content-module-update.component';
import { ContentModuleService } from 'app/entities/content-module/content-module.service';
import { ContentModule } from 'app/shared/model/content-module.model';

describe('Component Tests', () => {
  describe('ContentModule Management Update Component', () => {
    let comp: ContentModuleUpdateComponent;
    let fixture: ComponentFixture<ContentModuleUpdateComponent>;
    let service: ContentModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [ContentModuleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContentModuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentModuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentModuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentModule(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentModule();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
