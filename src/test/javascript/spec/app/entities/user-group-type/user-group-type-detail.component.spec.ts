import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { UserGroupTypeDetailComponent } from 'app/entities/user-group-type/user-group-type-detail.component';
import { UserGroupType } from 'app/shared/model/user-group-type.model';

describe('Component Tests', () => {
  describe('UserGroupType Management Detail Component', () => {
    let comp: UserGroupTypeDetailComponent;
    let fixture: ComponentFixture<UserGroupTypeDetailComponent>;
    const route = ({ data: of({ userGroupType: new UserGroupType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [UserGroupTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserGroupTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserGroupTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userGroupType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
