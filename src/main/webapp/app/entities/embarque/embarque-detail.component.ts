import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmbarque } from 'app/shared/model/embarque.model';

@Component({
    selector: 'jhi-embarque-detail',
    templateUrl: './embarque-detail.component.html'
})
export class EmbarqueDetailComponent implements OnInit {
    embarque: IEmbarque;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ embarque }) => {
            this.embarque = embarque;
        });
    }

    previousState() {
        window.history.back();
    }
}
