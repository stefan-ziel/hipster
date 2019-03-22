import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITransportadora } from 'app/shared/model/transportadora.model';
import { TransportadoraService } from './transportadora.service';

@Component({
    selector: 'jhi-transportadora-update',
    templateUrl: './transportadora-update.component.html'
})
export class TransportadoraUpdateComponent implements OnInit {
    transportadora: ITransportadora;
    isSaving: boolean;

    constructor(protected transportadoraService: TransportadoraService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ transportadora }) => {
            this.transportadora = transportadora;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.transportadora.id !== undefined) {
            this.subscribeToSaveResponse(this.transportadoraService.update(this.transportadora));
        } else {
            this.subscribeToSaveResponse(this.transportadoraService.create(this.transportadora));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportadora>>) {
        result.subscribe((res: HttpResponse<ITransportadora>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
