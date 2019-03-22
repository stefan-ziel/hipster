/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { EmbarcadoraDetailComponent } from 'app/entities/embarcadora/embarcadora-detail.component';
import { Embarcadora } from 'app/shared/model/embarcadora.model';

describe('Component Tests', () => {
    describe('Embarcadora Management Detail Component', () => {
        let comp: EmbarcadoraDetailComponent;
        let fixture: ComponentFixture<EmbarcadoraDetailComponent>;
        const route = ({ data: of({ embarcadora: new Embarcadora(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarcadoraDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmbarcadoraDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmbarcadoraDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.embarcadora).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
