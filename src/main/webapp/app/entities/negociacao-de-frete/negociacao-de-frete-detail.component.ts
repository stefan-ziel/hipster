import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

@Component({
    selector: 'jhi-negociacao-de-frete-detail',
    templateUrl: './negociacao-de-frete-detail.component.html'
})
export class NegociacaoDeFreteDetailComponent implements OnInit {
    negociacaoDeFrete: INegociacaoDeFrete;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ negociacaoDeFrete }) => {
            this.negociacaoDeFrete = negociacaoDeFrete;
        });
    }

    previousState() {
        window.history.back();
    }
}
