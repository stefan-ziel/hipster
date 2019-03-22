/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { EmbarqueUpdateComponent } from 'app/entities/embarque/embarque-update.component';
import { EmbarqueService } from 'app/entities/embarque/embarque.service';
import { Embarque } from 'app/shared/model/embarque.model';

describe('Component Tests', () => {
    describe('Embarque Management Update Component', () => {
        let comp: EmbarqueUpdateComponent;
        let fixture: ComponentFixture<EmbarqueUpdateComponent>;
        let service: EmbarqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarqueUpdateComponent]
            })
                .overrideTemplate(EmbarqueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmbarqueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmbarqueService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Embarque(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.embarque = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Embarque();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.embarque = entity;
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
