import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultado } from 'app/shared/model/resultado.model';

@Component({
    selector: 'jhi-resultado-detail',
    templateUrl: './resultado-detail.component.html'
})
export class ResultadoDetailComponent implements OnInit {
    resultado: IResultado;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultado }) => {
            this.resultado = resultado;
        });
    }

    previousState() {
        window.history.back();
    }
}
