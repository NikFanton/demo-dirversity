import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumDetailComponent } from 'app/entities/curriculum/curriculum-detail.component';
import { Curriculum } from 'app/shared/model/curriculum.model';

describe('Component Tests', () => {
  describe('Curriculum Management Detail Component', () => {
    let comp: CurriculumDetailComponent;
    let fixture: ComponentFixture<CurriculumDetailComponent>;
    const route = ({ data: of({ curriculum: new Curriculum(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CurriculumDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurriculumDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.curriculum).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
