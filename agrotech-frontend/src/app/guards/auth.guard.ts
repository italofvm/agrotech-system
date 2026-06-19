import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';

export const authGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const usuarioService: UsuarioService = inject(UsuarioService);
  const router: Router = inject(Router);

  if (usuarioService.estaLogado()) {
    return true;
  }

  return router.parseUrl('/login');
};
