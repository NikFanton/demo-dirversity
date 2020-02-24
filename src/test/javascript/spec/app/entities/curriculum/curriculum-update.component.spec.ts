import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumUpdateComponent } from 'app/entities/curriculum/curriculum-update.component';
import { CurriculumService } from 'app/entities/curriculum/curriculum.service';
import { Curriculum } from 'app/shared/model/curriculum.model';

describe('Component Tests', () => {
  describe('Curriculum Management Update Component', () => {
    let comp: CurriculumUpdateComponent;
    let fixture: ComponentFixture<CurriculumUpdateComponent>;
    let service: CurriculumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CurriculumUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurriculumUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurriculumService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Curriculum(123);
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
        const entity = new Curriculum();
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
