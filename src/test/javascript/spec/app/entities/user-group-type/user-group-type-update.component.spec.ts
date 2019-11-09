import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { UserGroupTypeUpdateComponent } from 'app/entities/user-group-type/user-group-type-update.component';
import { UserGroupTypeService } from 'app/entities/user-group-type/user-group-type.service';
import { UserGroupType } from 'app/shared/model/user-group-type.model';

describe('Component Tests', () => {
  describe('UserGroupType Management Update Component', () => {
    let comp: UserGroupTypeUpdateComponent;
    let fixture: ComponentFixture<UserGroupTypeUpdateComponent>;
    let service: UserGroupTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [UserGroupTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserGroupTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserGroupTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserGroupTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserGroupType(123);
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
        const entity = new UserGroupType();
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
