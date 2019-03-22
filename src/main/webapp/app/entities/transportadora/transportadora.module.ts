import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CdfSharedModule } from 'app/shared';
import {
    TransportadoraComponent,
    TransportadoraDetailComponent,
    TransportadoraUpdateComponent,
    TransportadoraDeletePopupComponent,
    TransportadoraDeleteDialogComponent,
    transportadoraRoute,
    transportadoraPopupRoute
} from './';

const ENTITY_STATES = [...transportadoraRoute, ...transportadoraPopupRoute];

@NgModule({
    imports: [CdfSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TransportadoraComponent,
        TransportadoraDetailComponent,
        TransportadoraUpdateComponent,
        TransportadoraDeleteDialogComponent,
        TransportadoraDeletePopupComponent
    ],
    entryComponents: [
        TransportadoraComponent,
        TransportadoraUpdateComponent,
        TransportadoraDeleteDialogComponent,
        TransportadoraDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfTransportadoraModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
