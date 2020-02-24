import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumTagUpdateComponent } from 'app/entities/curriculum-tag/curriculum-tag-update.component';
import { CurriculumTagService } from 'app/entities/curriculum-tag/curriculum-tag.service';
import { CurriculumTag } from 'app/shared/model/curriculum-tag.model';

describe('Component Tests', () => {
  describe('CurriculumTag Management Update Component', () => {
    let comp: CurriculumTagUpdateComponent;
    let fixture: ComponentFixture<CurriculumTagUpdateComponent>;
    let service: CurriculumTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumTagUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CurriculumTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurriculumTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurriculumTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CurriculumTag(123);
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
        const entity = new CurriculumTag();
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
