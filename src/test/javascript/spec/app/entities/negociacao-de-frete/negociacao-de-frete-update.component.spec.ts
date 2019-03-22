/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { NegociacaoDeFreteUpdateComponent } from 'app/entities/negociacao-de-frete/negociacao-de-frete-update.component';
import { NegociacaoDeFreteService } from 'app/entities/negociacao-de-frete/negociacao-de-frete.service';
import { NegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

describe('Component Tests', () => {
    describe('NegociacaoDeFrete Management Update Component', () => {
        let comp: NegociacaoDeFreteUpdateComponent;
        let fixture: ComponentFixture<NegociacaoDeFreteUpdateComponent>;
        let service: NegociacaoDeFreteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [NegociacaoDeFreteUpdateComponent]
            })
                .overrideTemplate(NegociacaoDeFreteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NegociacaoDeFreteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NegociacaoDeFreteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new NegociacaoDeFrete(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.negociacaoDeFrete = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new NegociacaoDeFrete();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.negociacaoDeFrete = entity;
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
