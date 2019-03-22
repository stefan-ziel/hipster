import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { AccountService } from 'app/core';
import { EmbarcadoraService } from './embarcadora.service';

@Component({
    selector: 'jhi-embarcadora',
    templateUrl: './embarcadora.component.html'
})
export class EmbarcadoraComponent implements OnInit, OnDestroy {
    embarcadoras: IEmbarcadora[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected embarcadoraService: EmbarcadoraService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.embarcadoraService
            .query()
            .pipe(
                filter((res: HttpResponse<IEmbarcadora[]>) => res.ok),
                map((res: HttpResponse<IEmbarcadora[]>) => res.body)
            )
            .subscribe(
                (res: IEmbarcadora[]) => {
                    this.embarcadoras = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmbarcadoras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmbarcadora) {
        return item.id;
    }

    registerChangeInEmbarcadoras() {
        this.eventSubscriber = this.eventManager.subscribe('embarcadoraListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
