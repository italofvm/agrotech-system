import { Routes } from '@angular/router';
import { CadastroComponent } from './components/usuario/cadastro/cadastro.component';
import { LoginComponent } from './components/usuario/login/login.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { SensorListComponent } from './components/sensores/sensor-list/sensor-list.component';
import { SensorDetailComponent } from './components/sensores/sensor-detail/sensor-detail.component';
import { DashboardComponent } from './components/sensores/dashboard/dashboard.component';
import { SensorLeituraFormComponent } from './components/sensores/sensor-leitura-form/sensor-leitura-form.component';
import { SensorFormComponent } from './components/sensores/sensor-form/sensor-form.component';
import { AreaListComponent } from './components/areas/area-list/area-list.component';
import { AreaFormComponent } from './components/areas/area-form/area-form.component';
import { RegraListComponent } from './components/regras/regra-list/regra-list.component';
import { RegraFormComponent } from './components/regras/regra-form/regra-form.component';
import { AlertaListComponent } from './components/alertas/alerta-list/alerta-list.component';
import { LeituraListComponent } from './components/sensores/leitura-list/leitura-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'login', component: LoginComponent },

  {
    path: 'sensores',
    canActivate: [authGuard],
    children: [
      { path: '', component: SensorListComponent },
      { path: 'detalhes/:id', component: SensorDetailComponent },
    ],
  },

  {
    path: 'telemetria',
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'simulador', component: SensorLeituraFormComponent },
      { path: 'leituras', component: LeituraListComponent },
    ],
  },

  {
    path: 'configuracoes',
    canActivate: [authGuard, adminGuard],
    children: [
      { path: 'novo-sensor', component: SensorFormComponent },
      { path: 'editar-sensor/:id', component: SensorFormComponent },
    ],
  },

  {
    path: 'areas',
    canActivate: [authGuard],
    children: [
      { path: '', component: AreaListComponent },
      { path: 'nova', component: AreaFormComponent },
      { path: 'editar/:id', component: AreaFormComponent },
    ],
  },

  {
    path: 'regras',
    canActivate: [authGuard],
    children: [
      { path: '', component: RegraListComponent },
      { path: 'nova', component: RegraFormComponent, canActivate: [adminGuard] },
    ],
  },

  {
    path: 'alertas',
    canActivate: [authGuard],
    children: [
      { path: '', component: AlertaListComponent },
    ],
  },

  {
    path: '**',
    redirectTo: 'login',
  },
];

