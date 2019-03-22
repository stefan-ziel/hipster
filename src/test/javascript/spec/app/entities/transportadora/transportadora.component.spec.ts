/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CdfTestModule } from '../../../test.module';
import { TransportadoraComponent } from 'app/entities/transportadora/transportadora.component';
import { TransportadoraService } from 'app/entities/transportadora/transportadora.service';
import { Transportadora } from 'app/shared/model/transportadora.model';

describe('Component Tests', () => {
    describe('Transportadora Management Component', () => {
        let comp: TransportadoraComponent;
        let fixture: ComponentFixture<TransportadoraComponent>;
        let service: TransportadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [TransportadoraComponent],
                providers: []
            })
                .overrideTemplate(TransportadoraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransportadoraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransportadoraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Transportadora(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.transportadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
