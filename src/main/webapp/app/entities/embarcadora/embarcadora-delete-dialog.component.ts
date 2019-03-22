import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { EmbarcadoraService } from './embarcadora.service';

@Component({
    selector: 'jhi-embarcadora-delete-dialog',
    templateUrl: './embarcadora-delete-dialog.component.html'
})
export class EmbarcadoraDeleteDialogComponent {
    embarcadora: IEmbarcadora;

    constructor(
        protected embarcadoraService: EmbarcadoraService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.embarcadoraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'embarcadoraListModification',
                content: 'Deleted an embarcadora'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-embarcadora-delete-popup',
    template: ''
})
export class EmbarcadoraDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ embarcadora }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmbarcadoraDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.embarcadora = embarcadora;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/embarcadora', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/embarcadora', { outlets: { popup: null } }]);
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
