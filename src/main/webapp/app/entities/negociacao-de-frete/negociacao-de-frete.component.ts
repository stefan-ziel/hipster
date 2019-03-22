import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';
import { AccountService } from 'app/core';
import { NegociacaoDeFreteService } from './negociacao-de-frete.service';

@Component({
    selector: 'jhi-negociacao-de-frete',
    templateUrl: './negociacao-de-frete.component.html'
})
export class NegociacaoDeFreteComponent implements OnInit, OnDestroy {
    negociacaoDeFretes: INegociacaoDeFrete[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected negociacaoDeFreteService: NegociacaoDeFreteService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.negociacaoDeFreteService
            .query()
            .pipe(
                filter((res: HttpResponse<INegociacaoDeFrete[]>) => res.ok),
                map((res: HttpResponse<INegociacaoDeFrete[]>) => res.body)
            )
            .subscribe(
                (res: INegociacaoDeFrete[]) => {
                    this.negociacaoDeFretes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNegociacaoDeFretes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INegociacaoDeFrete) {
        return item.id;
    }

    registerChangeInNegociacaoDeFretes() {
        this.eventSubscriber = this.eventManager.subscribe('negociacaoDeFreteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
