/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { ResultadoDetailComponent } from 'app/entities/resultado/resultado-detail.component';
import { Resultado } from 'app/shared/model/resultado.model';

describe('Component Tests', () => {
    describe('Resultado Management Detail Component', () => {
        let comp: ResultadoDetailComponent;
        let fixture: ComponentFixture<ResultadoDetailComponent>;
        const route = ({ data: of({ resultado: new Resultado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [ResultadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resultado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
