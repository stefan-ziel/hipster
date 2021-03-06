import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResultado } from 'app/shared/model/resultado.model';
import { IEmbarque } from 'app/shared/model/embarque.model';

type EntityResponseType = HttpResponse<IResultado>;
type EntityArrayResponseType = HttpResponse<IResultado[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoService {
    public resourceUrl = SERVER_API_URL + 'api/resultados';
    public calcUrlEmbarque = SERVER_API_URL + 'api/resultados-do-embarque';
    public calcUrlDia = SERVER_API_URL + 'api/resultados-do-dia';

    constructor(protected http: HttpClient) {}

    calcEmbarque(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IResultado>(`${this.calcUrlEmbarque}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IResultado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResultado[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.previsaoDeEntrega = res.body.previsaoDeEntrega != null ? moment(res.body.previsaoDeEntrega) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((resultado: IResultado) => {
                resultado.previsaoDeEntrega = resultado.previsaoDeEntrega != null ? moment(resultado.previsaoDeEntrega) : null;
            });
        }
        return res;
    }
}
