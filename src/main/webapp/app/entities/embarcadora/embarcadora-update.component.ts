import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { EmbarcadoraService } from './embarcadora.service';

@Component({
    selector: 'jhi-embarcadora-update',
    templateUrl: './embarcadora-update.component.html'
})
export class EmbarcadoraUpdateComponent implements OnInit {
    embarcadora: IEmbarcadora;
    isSaving: boolean;

    constructor(protected embarcadoraService: EmbarcadoraService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ embarcadora }) => {
            this.embarcadora = embarcadora;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.embarcadora.id !== undefined) {
            this.subscribeToSaveResponse(this.embarcadoraService.update(this.embarcadora));
        } else {
            this.subscribeToSaveResponse(this.embarcadoraService.create(this.embarcadora));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmbarcadora>>) {
        result.subscribe((res: HttpResponse<IEmbarcadora>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
