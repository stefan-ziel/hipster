import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';
import { NegociacaoDeFreteService } from './negociacao-de-frete.service';
import { NegociacaoDeFreteComponent } from './negociacao-de-frete.component';
import { NegociacaoDeFreteDetailComponent } from './negociacao-de-frete-detail.component';
import { NegociacaoDeFreteUpdateComponent } from './negociacao-de-frete-update.component';
import { NegociacaoDeFreteDeletePopupComponent } from './negociacao-de-frete-delete-dialog.component';
import { INegociacaoDeFrete } from 'app/shared/model/negociacao-de-frete.model';

@Injectable({ providedIn: 'root' })
export class NegociacaoDeFreteResolve implements Resolve<INegociacaoDeFrete> {
    constructor(private service: NegociacaoDeFreteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INegociacaoDeFrete> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NegociacaoDeFrete>) => response.ok),
                map((negociacaoDeFrete: HttpResponse<NegociacaoDeFrete>) => negociacaoDeFrete.body)
            );
        }
        return of(new NegociacaoDeFrete());
    }
}

export const negociacaoDeFreteRoute: Routes = [
    {
        path: '',
        component: NegociacaoDeFreteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.negociacaoDeFrete.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NegociacaoDeFreteDetailComponent,
        resolve: {
            negociacaoDeFrete: NegociacaoDeFreteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.negociacaoDeFrete.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NegociacaoDeFreteUpdateComponent,
        resolve: {
            negociacaoDeFrete: NegociacaoDeFreteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.negociacaoDeFrete.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NegociacaoDeFreteUpdateComponent,
        resolve: {
            negociacaoDeFrete: NegociacaoDeFreteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.negociacaoDeFrete.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const negociacaoDeFretePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NegociacaoDeFreteDeletePopupComponent,
        resolve: {
            negociacaoDeFrete: NegociacaoDeFreteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.negociacaoDeFrete.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
