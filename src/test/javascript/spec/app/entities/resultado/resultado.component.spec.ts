/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CdfTestModule } from '../../../test.module';
import { ResultadoComponent } from 'app/entities/resultado/resultado.component';
import { ResultadoService } from 'app/entities/resultado/resultado.service';
import { Resultado } from 'app/shared/model/resultado.model';

describe('Component Tests', () => {
    describe('Resultado Management Component', () => {
        let comp: ResultadoComponent;
        let fixture: ComponentFixture<ResultadoComponent>;
        let service: ResultadoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [ResultadoComponent],
                providers: []
            })
                .overrideTemplate(ResultadoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultadoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultadoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Resultado(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.resultados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
