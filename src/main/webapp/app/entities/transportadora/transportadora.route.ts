import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Transportadora } from 'app/shared/model/transportadora.model';
import { TransportadoraService } from './transportadora.service';
import { TransportadoraComponent } from './transportadora.component';
import { TransportadoraDetailComponent } from './transportadora-detail.component';
import { TransportadoraUpdateComponent } from './transportadora-update.component';
import { TransportadoraDeletePopupComponent } from './transportadora-delete-dialog.component';
import { ITransportadora } from 'app/shared/model/transportadora.model';

@Injectable({ providedIn: 'root' })
export class TransportadoraResolve implements Resolve<ITransportadora> {
    constructor(private service: TransportadoraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransportadora> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Transportadora>) => response.ok),
                map((transportadora: HttpResponse<Transportadora>) => transportadora.body)
            );
        }
        return of(new Transportadora());
    }
}

export const transportadoraRoute: Routes = [
    {
        path: '',
        component: TransportadoraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.transportadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TransportadoraDetailComponent,
        resolve: {
            transportadora: TransportadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.transportadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TransportadoraUpdateComponent,
        resolve: {
            transportadora: TransportadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.transportadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TransportadoraUpdateComponent,
        resolve: {
            transportadora: TransportadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.transportadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transportadoraPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TransportadoraDeletePopupComponent,
        resolve: {
            transportadora: TransportadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.transportadora.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
