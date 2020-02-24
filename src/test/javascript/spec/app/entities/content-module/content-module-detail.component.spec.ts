import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { ContentModuleDetailComponent } from 'app/entities/content-module/content-module-detail.component';
import { ContentModule } from 'app/shared/model/content-module.model';

describe('Component Tests', () => {
  describe('ContentModule Management Detail Component', () => {
    let comp: ContentModuleDetailComponent;
    let fixture: ComponentFixture<ContentModuleDetailComponent>;
    const route = ({ data: of({ contentModule: new ContentModule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [ContentModuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContentModuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentModuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentModule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
