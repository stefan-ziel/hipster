import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';
import { NegociacaoDeFreteService } from './negociacao-de-frete.service';

@Component({
    selector: 'jhi-negociacao-de-frete-delete-dialog',
    templateUrl: './negociacao-de-frete-delete-dialog.component.html'
})
export class NegociacaoDeFreteDeleteDialogComponent {
    negociacaoDeFrete: INegociacaoDeFrete;

    constructor(
        protected negociacaoDeFreteService: NegociacaoDeFreteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.negociacaoDeFreteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'negociacaoDeFreteListModification',
                content: 'Deleted an negociacaoDeFrete'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-negociacao-de-frete-delete-popup',
    template: ''
})
export class NegociacaoDeFreteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ negociacaoDeFrete }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NegociacaoDeFreteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.negociacaoDeFrete = negociacaoDeFrete;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/negociacao-de-frete', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/negociacao-de-frete', { outlets: { popup: null } }]);
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
