/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CdfTestModule } from '../../../test.module';
import { NegociacaoDeFreteComponent } from 'app/entities/negociacao-de-frete/negociacao-de-frete.component';
import { NegociacaoDeFreteService } from 'app/entities/negociacao-de-frete/negociacao-de-frete.service';
import { NegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

describe('Component Tests', () => {
    describe('NegociacaoDeFrete Management Component', () => {
        let comp: NegociacaoDeFreteComponent;
        let fixture: ComponentFixture<NegociacaoDeFreteComponent>;
        let service: NegociacaoDeFreteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [NegociacaoDeFreteComponent],
                providers: []
            })
                .overrideTemplate(NegociacaoDeFreteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NegociacaoDeFreteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NegociacaoDeFreteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new NegociacaoDeFrete(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.negociacaoDeFretes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
