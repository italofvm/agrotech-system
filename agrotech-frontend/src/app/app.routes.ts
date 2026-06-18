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

export const routes: Routes = [
  //Rota padrão da aplicação

  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Rotas públicas
  { path: 'cadastro', component: CadastroComponent },
  { path: 'login', component: LoginComponent },

  // Rotas restritas para usuario Logado
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
    ],
  },

  // Rotas restritas (Nivel: APenas ADMIN)
  {
    path: 'configuracoes',
    canActivate: [authGuard, adminGuard],
    children: [
      { path: 'novo-sensor', component: SensorFormComponent },
      { path: 'editar-sensor/:id', component: SensorFormComponent },
    ],
  },

  // Rotas de fallback para evitar erros de navegação
  {
    path: '**',
    redirectTo: 'login',
  },
];
