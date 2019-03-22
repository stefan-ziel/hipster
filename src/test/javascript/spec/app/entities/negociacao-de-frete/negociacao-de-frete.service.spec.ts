/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { NegociacaoDeFreteService } from 'app/entities/negociacao-de-frete/negociacao-de-frete.service';
import { INegociacaoDeFrete, NegociacaoDeFrete, CategoriaDeVeiculo } from 'app/shared/model/negociacao-de-frete.model';

describe('Service Tests', () => {
    describe('NegociacaoDeFrete Service', () => {
        let injector: TestBed;
        let service: NegociacaoDeFreteService;
        let httpMock: HttpTestingController;
        let elemDefault: INegociacaoDeFrete;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(NegociacaoDeFreteService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new NegociacaoDeFrete(0, CategoriaDeVeiculo.VUC, 0, 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a NegociacaoDeFrete', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new NegociacaoDeFrete(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a NegociacaoDeFrete', async () => {
                const returnedFromService = Object.assign(
                    {
                        categoriaDeVeiculo: 'BBBBBB',
                        pesoDe: 1,
                        pesoAte: 1,
                        precoPorQuilometro: 1,
                        prazoDeEntrega: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of NegociacaoDeFrete', async () => {
                const returnedFromService = Object.assign(
                    {
                        categoriaDeVeiculo: 'BBBBBB',
                        pesoDe: 1,
                        pesoAte: 1,
                        precoPorQuilometro: 1,
                        prazoDeEntrega: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a NegociacaoDeFrete', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
