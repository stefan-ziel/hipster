import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CdfSharedModule } from 'app/shared';
import {
    EmbarqueComponent,
    EmbarqueDetailComponent,
    EmbarqueUpdateComponent,
    EmbarqueDeletePopupComponent,
    EmbarqueDeleteDialogComponent,
    embarqueRoute,
    embarquePopupRoute
} from './';

const ENTITY_STATES = [...embarqueRoute, ...embarquePopupRoute];

@NgModule({
    imports: [CdfSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmbarqueComponent,
        EmbarqueDetailComponent,
        EmbarqueUpdateComponent,
        EmbarqueDeleteDialogComponent,
        EmbarqueDeletePopupComponent
    ],
    entryComponents: [EmbarqueComponent, EmbarqueUpdateComponent, EmbarqueDeleteDialogComponent, EmbarqueDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfEmbarqueModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
