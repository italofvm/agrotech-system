import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { UserRoleModel } from '../models/user-role.model';

export const adminGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const usuarioService: UsuarioService = inject(UsuarioService);
  const router: Router = inject(Router);
  const perfilUsuario = usuarioService.obterRole();

    console.log('AuthGuard URL:', state.url);
    console.log('Token no guard:', localStorage.getItem('token'));
    console.log('Está logado:', usuarioService.estaLogado());

  if (usuarioService.estaLogado() && perfilUsuario === UserRoleModel.ADMIN) {
    return true;
  }

  alert('Acesso negado. Você não possui privilégios de administrador.');

  return router.parseUrl('/sensores');
};
