import { inject } from '@angular/core';
import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';

export const authGuard: CanActivateFn = (route, state): boolean | UrlTree => {
  const usuarioService: UsuarioService = inject(UsuarioService);
  const router: Router = inject(Router);

  console.log('URL tentando acessar:', state.url);
  console.log('Token:', localStorage.getItem('token'));
  console.log('Está logado:', usuarioService.estaLogado());

  if (usuarioService.estaLogado()) {
    return true;
  }

  return router.parseUrl('/login');
};
