import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'embarcadora',
                loadChildren: './embarcadora/embarcadora.module#CdfEmbarcadoraModule'
            },
            {
                path: 'transportadora',
                loadChildren: './transportadora/transportadora.module#CdfTransportadoraModule'
            },
            {
                path: 'embarque',
                loadChildren: './embarque/embarque.module#CdfEmbarqueModule'
            },
            {
                path: 'negociacao-de-frete',
                loadChildren: './negociacao-de-frete/negociacao-de-frete.module#CdfNegociacaoDeFreteModule'
            },
            {
                path: 'resultado',
                loadChildren: './resultado/resultado.module#CdfResultadoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CdfEntityModule {}
