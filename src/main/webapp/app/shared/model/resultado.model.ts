import { Moment } from 'moment';
import { IEmbarque } from 'app/shared/model/embarque.model';
import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

export interface IResultado {
    id?: number;
    valorCalculado?: number;
    previsaoDeEntrega?: Moment;
    embarque?: IEmbarque;
    melhorNegociacaoDeFrete?: INegociacaoDeFrete;
}

export class Resultado implements IResultado {
    constructor(
        public id?: number,
        public valorCalculado?: number,
        public previsaoDeEntrega?: Moment,
        public embarque?: IEmbarque,
        public melhorNegociacaoDeFrete?: INegociacaoDeFrete
    ) {}
}
