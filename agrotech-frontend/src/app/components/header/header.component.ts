import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MaterialModule } from '../../material/material.module';
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
  styleUrls: './header.component.css'
})

export class HeaderComponent {
  private servicoUsuario = new UsuarioService();
  private roteador = inject(Router);

  public estaLogado = computed(() => this.servicoUsuario.estaLogado());

  public ehAdmin = computed(() => 
    this.servicoUsuario.estaLogado() &&
    this.servicoUsuario.obterRole() === UserRoleModel.ADMIN);

  sair(): void {
    this.servicoUsuario.logout();
    this.roteador.navigate(['/login']);
  }
}

