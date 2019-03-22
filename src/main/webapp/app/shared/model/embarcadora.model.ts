export interface IEmbarcadora {
    id?: number;
    nome?: string;
}

export class Embarcadora implements IEmbarcadora {
    constructor(public id?: number, public nome?: string) {}
}
