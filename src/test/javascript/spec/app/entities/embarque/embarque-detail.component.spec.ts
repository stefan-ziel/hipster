/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CdfTestModule } from '../../../test.module';
import { EmbarqueDetailComponent } from 'app/entities/embarque/embarque-detail.component';
import { Embarque } from 'app/shared/model/embarque.model';

describe('Component Tests', () => {
    describe('Embarque Management Detail Component', () => {
        let comp: EmbarqueDetailComponent;
        let fixture: ComponentFixture<EmbarqueDetailComponent>;
        const route = ({ data: of({ embarque: new Embarque(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarqueDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmbarqueDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmbarqueDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.embarque).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
