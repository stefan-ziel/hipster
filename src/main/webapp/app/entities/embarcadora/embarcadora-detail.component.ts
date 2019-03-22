import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmbarcadora } from 'app/shared/model/embarcadora.model';

@Component({
    selector: 'jhi-embarcadora-detail',
    templateUrl: './embarcadora-detail.component.html'
})
export class EmbarcadoraDetailComponent implements OnInit {
    embarcadora: IEmbarcadora;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ embarcadora }) => {
            this.embarcadora = embarcadora;
        });
    }

    previousState() {
        window.history.back();
    }
}
