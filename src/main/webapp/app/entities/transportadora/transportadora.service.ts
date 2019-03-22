import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransportadora } from 'app/shared/model/transportadora.model';

type EntityResponseType = HttpResponse<ITransportadora>;
type EntityArrayResponseType = HttpResponse<ITransportadora[]>;

@Injectable({ providedIn: 'root' })
export class TransportadoraService {
    public resourceUrl = SERVER_API_URL + 'api/transportadoras';

    constructor(protected http: HttpClient) {}

    create(transportadora: ITransportadora): Observable<EntityResponseType> {
        return this.http.post<ITransportadora>(this.resourceUrl, transportadora, { observe: 'response' });
    }

    update(transportadora: ITransportadora): Observable<EntityResponseType> {
        return this.http.put<ITransportadora>(this.resourceUrl, transportadora, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITransportadora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITransportadora[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
