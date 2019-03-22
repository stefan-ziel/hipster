import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Embarcadora } from 'app/shared/model/embarcadora.model';
import { EmbarcadoraService } from './embarcadora.service';
import { EmbarcadoraComponent } from './embarcadora.component';
import { EmbarcadoraDetailComponent } from './embarcadora-detail.component';
import { EmbarcadoraUpdateComponent } from './embarcadora-update.component';
import { EmbarcadoraDeletePopupComponent } from './embarcadora-delete-dialog.component';
import { IEmbarcadora } from 'app/shared/model/embarcadora.model';

@Injectable({ providedIn: 'root' })
export class EmbarcadoraResolve implements Resolve<IEmbarcadora> {
    constructor(private service: EmbarcadoraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmbarcadora> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Embarcadora>) => response.ok),
                map((embarcadora: HttpResponse<Embarcadora>) => embarcadora.body)
            );
        }
        return of(new Embarcadora());
    }
}

export const embarcadoraRoute: Routes = [
    {
        path: '',
        component: EmbarcadoraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.embarcadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EmbarcadoraDetailComponent,
        resolve: {
            embarcadora: EmbarcadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.embarcadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EmbarcadoraUpdateComponent,
        resolve: {
            embarcadora: EmbarcadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.embarcadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EmbarcadoraUpdateComponent,
        resolve: {
            embarcadora: EmbarcadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.embarcadora.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const embarcadoraPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EmbarcadoraDeletePopupComponent,
        resolve: {
            embarcadora: EmbarcadoraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cdfApp.embarcadora.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
