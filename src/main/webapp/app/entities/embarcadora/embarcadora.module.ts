import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CdfSharedModule } from 'app/shared';
import {
    EmbarcadoraComponent,
    EmbarcadoraDetailComponent,
    EmbarcadoraUpdateComponent,
    EmbarcadoraDeletePopupComponent,
    EmbarcadoraDeleteDialogComponent,
    embarcadoraRoute,
    embarcadoraPopupRoute
} from './';

const ENTITY_STATES = [...embarcadoraRoute, ...embarcadoraPopupRoute];

@NgModule({
    imports: [CdfSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmbarcadoraComponent,
        EmbarcadoraDetailComponent,
        EmbarcadoraUpdateComponent,
        EmbarcadoraDeleteDialogComponent,
        EmbarcadoraDeletePopupComponent
    ],
    entryComponents: [EmbarcadoraComponent, EmbarcadoraUpdateComponent, EmbarcadoraDeleteDialogComponent, EmbarcadoraDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfEmbarcadoraModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
