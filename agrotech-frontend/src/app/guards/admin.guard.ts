import { Inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { UserRoleModel } from '../models/user-role.model';

export const adminGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const usuarioService: UsuarioService = Inject(UsuarioService);
  const router: Router = Inject(Router);
  const perfilUsuario = usuarioService.obterRole();

  if (usuarioService.estaLogado() && perfilUsuario === UserRoleModel.ADMIN) {
    return true;
  }

  alert('Acesso negado. Você não possui privilégios de administrador.');

  return router.parseUrl('/login');
};
