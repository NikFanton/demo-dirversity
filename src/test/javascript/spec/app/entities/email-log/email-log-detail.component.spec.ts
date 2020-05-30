import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DirversityTestModule } from '../../../test.module';
import { EmailLogDetailComponent } from 'app/entities/email-log/email-log-detail.component';
import { EmailLog } from 'app/shared/model/email-log.model';

describe('Component Tests', () => {
  describe('EmailLog Management Detail Component', () => {
    let comp: EmailLogDetailComponent;
    let fixture: ComponentFixture<EmailLogDetailComponent>;
    const route = ({ data: of({ emailLog: new EmailLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DirversityTestModule],
        declarations: [EmailLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmailLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
