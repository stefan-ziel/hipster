export interface ITransportadora {
    id?: number;
    nome?: string;
}

export class Transportadora implements ITransportadora {
    constructor(public id?: number, public nome?: string) {}
}
