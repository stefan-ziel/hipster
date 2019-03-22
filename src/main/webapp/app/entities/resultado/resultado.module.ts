import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CdfSharedModule } from 'app/shared';
import { ResultadoComponent, ResultadoDetailComponent, resultadoRoute } from './';

const ENTITY_STATES = [...resultadoRoute];

@NgModule({
    imports: [CdfSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ResultadoComponent, ResultadoDetailComponent],
    entryComponents: [ResultadoComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfResultadoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
