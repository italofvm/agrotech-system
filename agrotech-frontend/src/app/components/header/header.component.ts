import { Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MaterialModule } from '../../shared/material/material.module';
import { UsuarioService } from '../../services/usuario.service';
import { UserRoleModel } from '../../models/user-role.model';

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
  private servicoUsuario = inject(UsuarioService);
  private roteador = inject(Router);

  estaLogado(): boolean {
    return this.servicoUsuario.estaLogado();
  }

  ehAdmin(): boolean {
    return (
      this.servicoUsuario.estaLogado() &&
      this.servicoUsuario.obterRole() === UserRoleModel.ADMIN
    );
  }

  sair(): void {
    this.servicoUsuario.logout();
    this.roteador.navigate(['/login']);
  }
}

