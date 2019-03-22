/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CdfTestModule } from '../../../test.module';
import { EmbarqueDeleteDialogComponent } from 'app/entities/embarque/embarque-delete-dialog.component';
import { EmbarqueService } from 'app/entities/embarque/embarque.service';

describe('Component Tests', () => {
    describe('Embarque Management Delete Component', () => {
        let comp: EmbarqueDeleteDialogComponent;
        let fixture: ComponentFixture<EmbarqueDeleteDialogComponent>;
        let service: EmbarqueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CdfTestModule],
                declarations: [EmbarqueDeleteDialogComponent]
            })
                .overrideTemplate(EmbarqueDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmbarqueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmbarqueService);
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
