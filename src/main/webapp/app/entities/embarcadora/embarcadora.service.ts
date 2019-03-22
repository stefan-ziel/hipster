import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';

type EntityResponseType = HttpResponse<IEmbarcadora>;
type EntityArrayResponseType = HttpResponse<IEmbarcadora[]>;

@Injectable({ providedIn: 'root' })
export class EmbarcadoraService {
    public resourceUrl = SERVER_API_URL + 'api/embarcadoras';

    constructor(protected http: HttpClient) {}

    create(embarcadora: IEmbarcadora): Observable<EntityResponseType> {
        return this.http.post<IEmbarcadora>(this.resourceUrl, embarcadora, { observe: 'response' });
    }

    update(embarcadora: IEmbarcadora): Observable<EntityResponseType> {
        return this.http.put<IEmbarcadora>(this.resourceUrl, embarcadora, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEmbarcadora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmbarcadora[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
