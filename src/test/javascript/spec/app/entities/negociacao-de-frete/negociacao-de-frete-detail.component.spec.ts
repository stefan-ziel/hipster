/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { NegociacaoDeFreteDetailComponent } from 'app/entities/negociacao-de-frete/negociacao-de-frete-detail.component';
import { NegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

describe('Component Tests', () => {
    describe('NegociacaoDeFrete Management Detail Component', () => {
        let comp: NegociacaoDeFreteDetailComponent;
        let fixture: ComponentFixture<NegociacaoDeFreteDetailComponent>;
        const route = ({ data: of({ negociacaoDeFrete: new NegociacaoDeFrete(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [NegociacaoDeFreteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NegociacaoDeFreteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NegociacaoDeFreteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.negociacaoDeFrete).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
