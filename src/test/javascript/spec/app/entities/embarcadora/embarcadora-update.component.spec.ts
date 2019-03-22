/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { EmbarcadoraUpdateComponent } from 'app/entities/embarcadora/embarcadora-update.component';
import { EmbarcadoraService } from 'app/entities/embarcadora/embarcadora.service';
import { Embarcadora } from 'app/shared/model/embarcadora.model';

describe('Component Tests', () => {
    describe('Embarcadora Management Update Component', () => {
        let comp: EmbarcadoraUpdateComponent;
        let fixture: ComponentFixture<EmbarcadoraUpdateComponent>;
        let service: EmbarcadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarcadoraUpdateComponent]
            })
                .overrideTemplate(EmbarcadoraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmbarcadoraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmbarcadoraService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Embarcadora(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.embarcadora = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Embarcadora();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.embarcadora = entity;
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
