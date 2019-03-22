import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmbarque } from 'app/shared/model/embarque.model';
import { EmbarqueService } from './embarque.service';

@Component({
    selector: 'jhi-embarque-delete-dialog',
    templateUrl: './embarque-delete-dialog.component.html'
})
export class EmbarqueDeleteDialogComponent {
    embarque: IEmbarque;

    constructor(protected embarqueService: EmbarqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.embarqueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'embarqueListModification',
                content: 'Deleted an embarque'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-embarque-delete-popup',
    template: ''
})
export class EmbarqueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ embarque }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmbarqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.embarque = embarque;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/embarque', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/embarque', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
