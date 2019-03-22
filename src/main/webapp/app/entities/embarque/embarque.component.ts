import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmbarque } from 'app/shared/model/embarque.model';
import { AccountService } from 'app/core';
import { EmbarqueService } from './embarque.service';

@Component({
    selector: 'jhi-embarque',
    templateUrl: './embarque.component.html'
})
export class EmbarqueComponent implements OnInit, OnDestroy {
    embarques: IEmbarque[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected embarqueService: EmbarqueService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.embarqueService
            .query()
            .pipe(
                filter((res: HttpResponse<IEmbarque[]>) => res.ok),
                map((res: HttpResponse<IEmbarque[]>) => res.body)
            )
            .subscribe(
                (res: IEmbarque[]) => {
                    this.embarques = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmbarques();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmbarque) {
        return item.id;
    }

    registerChangeInEmbarques() {
        this.eventSubscriber = this.eventManager.subscribe('embarqueListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
