/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CdfTestModule } from '../../../test.module';
import { TransportadoraDeleteDialogComponent } from 'app/entities/transportadora/transportadora-delete-dialog.component';
import { TransportadoraService } from 'app/entities/transportadora/transportadora.service';

describe('Component Tests', () => {
    describe('Transportadora Management Delete Component', () => {
        let comp: TransportadoraDeleteDialogComponent;
        let fixture: ComponentFixture<TransportadoraDeleteDialogComponent>;
        let service: TransportadoraService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [TransportadoraDeleteDialogComponent]
            })
                .overrideTemplate(TransportadoraDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransportadoraDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransportadoraService);
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
