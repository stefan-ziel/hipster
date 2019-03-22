import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CdfSharedModule } from 'app/shared';
import {
    NegociacaoDeFreteComponent,
    NegociacaoDeFreteDetailComponent,
    NegociacaoDeFreteUpdateComponent,
    NegociacaoDeFreteDeletePopupComponent,
    NegociacaoDeFreteDeleteDialogComponent,
    negociacaoDeFreteRoute,
    negociacaoDeFretePopupRoute
} from './';

const ENTITY_STATES = [...negociacaoDeFreteRoute, ...negociacaoDeFretePopupRoute];

@NgModule({
    imports: [CdfSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NegociacaoDeFreteComponent,
        NegociacaoDeFreteDetailComponent,
        NegociacaoDeFreteUpdateComponent,
        NegociacaoDeFreteDeleteDialogComponent,
        NegociacaoDeFreteDeletePopupComponent
    ],
    entryComponents: [
        NegociacaoDeFreteComponent,
        NegociacaoDeFreteUpdateComponent,
        NegociacaoDeFreteDeleteDialogComponent,
        NegociacaoDeFreteDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfNegociacaoDeFreteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
