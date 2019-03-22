import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';
import { NegociacaoDeFreteService } from './negociacao-de-frete.service';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { EmbarcadoraService } from 'app/entities/embarcadora';
import { ITransportadora } from 'app/shared/model/transportadora.model';
import { TransportadoraService } from 'app/entities/transportadora';

@Component({
    selector: 'jhi-negociacao-de-frete-update',
    templateUrl: './negociacao-de-frete-update.component.html'
})
export class NegociacaoDeFreteUpdateComponent implements OnInit {
    negociacaoDeFrete: INegociacaoDeFrete;
    isSaving: boolean;

    embarcadoras: IEmbarcadora[];

    transportadoras: ITransportadora[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected negociacaoDeFreteService: NegociacaoDeFreteService,
        protected embarcadoraService: EmbarcadoraService,
        protected transportadoraService: TransportadoraService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ negociacaoDeFrete }) => {
            this.negociacaoDeFrete = negociacaoDeFrete;
        });
        this.embarcadoraService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmbarcadora[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmbarcadora[]>) => response.body)
            )
            .subscribe((res: IEmbarcadora[]) => (this.embarcadoras = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.transportadoraService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITransportadora[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITransportadora[]>) => response.body)
            )
            .subscribe((res: ITransportadora[]) => (this.transportadoras = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.negociacaoDeFrete.id !== undefined) {
            this.subscribeToSaveResponse(this.negociacaoDeFreteService.update(this.negociacaoDeFrete));
        } else {
            this.subscribeToSaveResponse(this.negociacaoDeFreteService.create(this.negociacaoDeFrete));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INegociacaoDeFrete>>) {
        result.subscribe((res: HttpResponse<INegociacaoDeFrete>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmbarcadoraById(index: number, item: IEmbarcadora) {
        return item.id;
    }

    trackTransportadoraById(index: number, item: ITransportadora) {
        return item.id;
    }
}
