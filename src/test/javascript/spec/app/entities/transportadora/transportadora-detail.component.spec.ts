/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { TransportadoraDetailComponent } from 'app/entities/transportadora/transportadora-detail.component';
import { Transportadora } from 'app/shared/model/transportadora.model';

describe('Component Tests', () => {
    describe('Transportadora Management Detail Component', () => {
        let comp: TransportadoraDetailComponent;
        let fixture: ComponentFixture<TransportadoraDetailComponent>;
        const route = ({ data: of({ transportadora: new Transportadora(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [TransportadoraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TransportadoraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransportadoraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.transportadora).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
