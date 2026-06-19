import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { UserRoleModel } from '../models/user-role.model';

export const adminGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const usuarioService: UsuarioService = inject(UsuarioService);
  const router: Router = inject(Router);
  const perfilUsuario = usuarioService.obterRole();

  if (usuarioService.estaLogado() && perfilUsuario === UserRoleModel.ADMIN) {
    return true;
  }

  return router.parseUrl('/sensores');
};
