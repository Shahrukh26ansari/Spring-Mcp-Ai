import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'query',
    loadComponent: () =>
      import('./pages/query/query.component').then(m => m.QueryComponent)
  },
  { path: '**', redirectTo: '' }
];
