/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { TransportadoraUpdateComponent } from 'app/entities/transportadora/transportadora-update.component';
import { TransportadoraService } from 'app/entities/transportadora/transportadora.service';
import { Transportadora } from 'app/shared/model/transportadora.model';

describe('Component Tests', () => {
    describe('Transportadora Management Update Component', () => {
        let comp: TransportadoraUpdateComponent;
        let fixture: ComponentFixture<TransportadoraUpdateComponent>;
        let service: TransportadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [TransportadoraUpdateComponent]
            })
                .overrideTemplate(TransportadoraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransportadoraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransportadoraService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Transportadora(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.transportadora = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Transportadora();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.transportadora = entity;
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
