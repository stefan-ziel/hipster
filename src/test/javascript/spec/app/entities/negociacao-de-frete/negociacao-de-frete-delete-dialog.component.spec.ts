/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CdfTestModule } from '../../../test.module';
import { NegociacaoDeFreteDeleteDialogComponent } from 'app/entities/negociacao-de-frete/negociacao-de-frete-delete-dialog.component';
import { NegociacaoDeFreteService } from 'app/entities/negociacao-de-frete/negociacao-de-frete.service';

describe('Component Tests', () => {
    describe('NegociacaoDeFrete Management Delete Component', () => {
        let comp: NegociacaoDeFreteDeleteDialogComponent;
        let fixture: ComponentFixture<NegociacaoDeFreteDeleteDialogComponent>;
        let service: NegociacaoDeFreteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [NegociacaoDeFreteDeleteDialogComponent]
            })
                .overrideTemplate(NegociacaoDeFreteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NegociacaoDeFreteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NegociacaoDeFreteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
