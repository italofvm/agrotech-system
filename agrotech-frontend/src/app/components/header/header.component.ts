import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MaterialModule } from '../../shared/material/material.module';
import { UsuarioService } from '../../services/usuario.service';
import { UserRoleModel } from '../../models/user-role.model';
import { AlertaNotificacaoService } from '../../services/alerta-notificacao.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    MaterialModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  private servicoUsuario      = inject(UsuarioService);
  private roteador            = inject(Router);
  private alertaNotificacao   = inject(AlertaNotificacaoService);

  totalAlertasNovos = this.alertaNotificacao.totalNovos;

  estaLogado(): boolean {
    return this.servicoUsuario.estaLogado();
  }

  ehAdmin(): boolean {
    return (
      this.servicoUsuario.estaLogado() &&
      this.servicoUsuario.obterRole() === UserRoleModel.ADMIN
    );
  }

  irParaAlertas(): void {
    this.alertaNotificacao.reconhecer();
    this.roteador.navigate(['/alertas']);
  }

  sair(): void {
    this.servicoUsuario.logout();
    this.roteador.navigate(['/login']);
  }
}

