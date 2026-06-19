import { Component, effect, inject, signal } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './shared/material/material.module';
import { UsuarioService } from './services/usuario.service';
import { UserRoleModel } from './models/user-role.model';
import { AlertaNotificacaoService } from './services/alerta-notificacao.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule, MaterialModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular-agro-sensores';

  private usuarioService          = inject(UsuarioService);
  private router                  = inject(Router);
  private alertaNotificacao       = inject(AlertaNotificacaoService);

  sidebarAberta = signal<boolean>(true);

  constructor() {
    // Conecta/desconecta o SSE automaticamente conforme o estado de autenticação
    effect(() => {
      if (this.usuarioService.logado()) {
        this.alertaNotificacao.conectar();
      } else {
        this.alertaNotificacao.desconectar();
      }
    });
  }

  estaLogado(): boolean {
    return this.usuarioService.estaLogado();
  }

  ehAdmin(): boolean {
    return this.usuarioService.obterRole() === UserRoleModel.ADMIN;
  }

  sair(): void {
    this.usuarioService.logout();
    this.router.navigate(['/login']);
  }

  toggleSidebar(): void {
    this.sidebarAberta.update(v => !v);
  }
}
