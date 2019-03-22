import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

type EntityResponseType = HttpResponse<INegociacaoDeFrete>;
type EntityArrayResponseType = HttpResponse<INegociacaoDeFrete[]>;

@Injectable({ providedIn: 'root' })
export class NegociacaoDeFreteService {
    public resourceUrl = SERVER_API_URL + 'api/negociacao-de-fretes';

    constructor(protected http: HttpClient) {}

    create(negociacaoDeFrete: INegociacaoDeFrete): Observable<EntityResponseType> {
        return this.http.post<INegociacaoDeFrete>(this.resourceUrl, negociacaoDeFrete, { observe: 'response' });
    }

    update(negociacaoDeFrete: INegociacaoDeFrete): Observable<EntityResponseType> {
        return this.http.put<INegociacaoDeFrete>(this.resourceUrl, negociacaoDeFrete, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INegociacaoDeFrete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INegociacaoDeFrete[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
