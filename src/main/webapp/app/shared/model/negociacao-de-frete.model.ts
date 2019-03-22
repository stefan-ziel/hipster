import { IEmbarcadora } from 'app/shared/model/embarcadora.model';
import { ITransportadora } from 'app/shared/model/transportadora.model';

export const enum CategoriaDeVeiculo {
    VUC = 'VUC',
    TOCO = 'TOCO',
    VAN = 'VAN',
    TRUCK = 'TRUCK',
    CARRETA = 'CARRETA'
}

export interface INegociacaoDeFrete {
    id?: number;
    categoriaDeVeiculo?: CategoriaDeVeiculo;
    pesoDe?: number;
    pesoAte?: number;
    precoPorQuilometro?: number;
    prazoDeEntrega?: number;
    embarcadora?: IEmbarcadora;
    transportadora?: ITransportadora;
}

export class NegociacaoDeFrete implements INegociacaoDeFrete {
    constructor(
        public id?: number,
        public categoriaDeVeiculo?: CategoriaDeVeiculo,
        public pesoDe?: number,
        public pesoAte?: number,
        public precoPorQuilometro?: number,
        public prazoDeEntrega?: number,
        public embarcadora?: IEmbarcadora,
        public transportadora?: ITransportadora
    ) {}
}
