/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CdfTestModule } from '../../../test.module';
import { EmbarqueComponent } from 'app/entities/embarque/embarque.component';
import { EmbarqueService } from 'app/entities/embarque/embarque.service';
import { Embarque } from 'app/shared/model/embarque.model';

describe('Component Tests', () => {
    describe('Embarque Management Component', () => {
        let comp: EmbarqueComponent;
        let fixture: ComponentFixture<EmbarqueComponent>;
        let service: EmbarqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarqueComponent],
                providers: []
            })
                .overrideTemplate(EmbarqueComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmbarqueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmbarqueService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Embarque(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.embarques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
