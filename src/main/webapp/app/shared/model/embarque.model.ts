import { Moment } from 'moment';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';

export const enum CategoriaDeVeiculo {
    VUC = 'VUC',
    TOCO = 'TOCO',
    VAN = 'VAN',
    TRUCK = 'TRUCK',
    CARRETA = 'CARRETA'
}

export interface IEmbarque {
    id?: number;
    categoriaDeVeiculo?: CategoriaDeVeiculo;
    origem?: string;
    destino?: string;
    qilometragem?: number;
    peso?: number;
    dataDeColeta?: Moment;
    embarcadora?: IEmbarcadora;
}

export class Embarque implements IEmbarque {
    constructor(
        public id?: number,
        public categoriaDeVeiculo?: CategoriaDeVeiculo,
        public origem?: string,
        public destino?: string,
        public qilometragem?: number,
        public peso?: number,
        public dataDeColeta?: Moment,
        public embarcadora?: IEmbarcadora
    ) {}
}
