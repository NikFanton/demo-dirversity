import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { CurriculumTagDetailComponent } from 'app/entities/curriculum-tag/curriculum-tag-detail.component';
import { CurriculumTag } from 'app/shared/model/curriculum-tag.model';

describe('Component Tests', () => {
  describe('CurriculumTag Management Detail Component', () => {
    let comp: CurriculumTagDetailComponent;
    let fixture: ComponentFixture<CurriculumTagDetailComponent>;
    const route = ({ data: of({ curriculumTag: new CurriculumTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [CurriculumTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CurriculumTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurriculumTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.curriculumTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
