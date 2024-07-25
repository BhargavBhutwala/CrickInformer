import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HistoryComponent } from './components/history/history.component';
import { LiveComponent } from './components/live/live.component';
import { PointTableComponent } from './components/point-table/point-table.component';

export const routes: Routes = [
    {
        path: "",
        redirectTo: "/live",
        pathMatch: "full"
    },
    {
        path: "home",
        component: HomeComponent,
        title: "Home | CrickInformer",
    },
    {
        path: "history",
        component: HistoryComponent,
        title: "Match History | CrickInformer"
    },
    {
        path: "live",
        component: LiveComponent,
        title: "Live Score | CrickInformer"
    },
    {
        path: "point-table",
        component: PointTableComponent,
        title: "Points Table | CrickInformer"
    }
];
