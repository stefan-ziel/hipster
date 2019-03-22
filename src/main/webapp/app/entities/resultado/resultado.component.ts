import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResultado } from 'app/shared/model/resultado.model';
import { AccountService } from 'app/core';
import { ResultadoService } from './resultado.service';

@Component({
    selector: 'jhi-resultado',
    templateUrl: './resultado.component.html'
})
export class ResultadoComponent implements OnInit, OnDestroy {
    resultados: IResultado[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected resultadoService: ResultadoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.resultadoService
            .query()
            .pipe(
                filter((res: HttpResponse<IResultado[]>) => res.ok),
                map((res: HttpResponse<IResultado[]>) => res.body)
            )
            .subscribe(
                (res: IResultado[]) => {
                    this.resultados = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResultados();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResultado) {
        return item.id;
    }

    registerChangeInResultados() {
        this.eventSubscriber = this.eventManager.subscribe('resultadoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
