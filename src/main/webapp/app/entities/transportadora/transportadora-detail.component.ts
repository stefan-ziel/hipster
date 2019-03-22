import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransportadora } from 'app/shared/model/transportadora.model';

@Component({
    selector: 'jhi-transportadora-detail',
    templateUrl: './transportadora-detail.component.html'
})
export class TransportadoraDetailComponent implements OnInit {
    transportadora: ITransportadora;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transportadora }) => {
            this.transportadora = transportadora;
        });
    }

    previousState() {
        window.history.back();
    }
}
