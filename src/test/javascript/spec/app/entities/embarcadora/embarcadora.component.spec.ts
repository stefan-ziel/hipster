/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CdfTestModule } from '../../../test.module';
import { EmbarcadoraComponent } from 'app/entities/embarcadora/embarcadora.component';
import { EmbarcadoraService } from 'app/entities/embarcadora/embarcadora.service';
import { Embarcadora } from 'app/shared/model/embarcadora.model';

describe('Component Tests', () => {
    describe('Embarcadora Management Component', () => {
        let comp: EmbarcadoraComponent;
        let fixture: ComponentFixture<EmbarcadoraComponent>;
        let service: EmbarcadoraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarcadoraComponent],
                providers: []
            })
                .overrideTemplate(EmbarcadoraComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmbarcadoraComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmbarcadoraService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Embarcadora(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.embarcadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
