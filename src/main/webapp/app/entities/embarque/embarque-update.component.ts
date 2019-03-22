import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEmbarque } from 'app/shared/model/embarque.model';
import { EmbarqueService } from './embarque.service';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { EmbarcadoraService } from 'app/entities/embarcadora';

@Component({
    selector: 'jhi-embarque-update',
    templateUrl: './embarque-update.component.html'
})
export class EmbarqueUpdateComponent implements OnInit {
    embarque: IEmbarque;
    isSaving: boolean;

    embarcadoras: IEmbarcadora[];
    dataDeColeta: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected embarqueService: EmbarqueService,
        protected embarcadoraService: EmbarcadoraService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ embarque }) => {
            this.embarque = embarque;
            this.dataDeColeta = this.embarque.dataDeColeta != null ? this.embarque.dataDeColeta.format(DATE_TIME_FORMAT) : null;
        });
        this.embarcadoraService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmbarcadora[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmbarcadora[]>) => response.body)
            )
            .subscribe((res: IEmbarcadora[]) => (this.embarcadoras = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.embarque.dataDeColeta = this.dataDeColeta != null ? moment(this.dataDeColeta, DATE_TIME_FORMAT) : null;
        if (this.embarque.id !== undefined) {
            this.subscribeToSaveResponse(this.embarqueService.update(this.embarque));
        } else {
            this.subscribeToSaveResponse(this.embarqueService.create(this.embarque));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmbarque>>) {
        result.subscribe((res: HttpResponse<IEmbarque>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
