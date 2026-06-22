import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

/** Rotas públicas que não precisam de token */
const ROTAS_PUBLICAS = ['/auth/login', '/cadastro', '/alertas/stream'];

function rotaPublica(url: string): boolean {
  return ROTAS_PUBLICAS.some(r => url.includes(r));
}

function tokenEstaExpirado(token: string): boolean {
  try {
    const decoded: any = jwtDecode(token);
    return decoded.exp ? decoded.exp * 1000 < Date.now() : false;
  } catch {
    return true;
  }
}

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  const router = inject(Router);
  const snackBar = inject(MatSnackBar);

  if (!token) {
    if (!rotaPublica(req.url)) {
      router.navigate(['/login']);
      return throwError(() => new Error('Não autenticado'));
    }
    return next(req);
  }

  if (tokenEstaExpirado(token)) {
    localStorage.removeItem('token');
    router.navigate(['/login']);
    return throwError(() => new Error('Sessão expirada'));
  }

  const reqComToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(reqComToken).pipe(
    catchError((erro: HttpErrorResponse) => {
      if (erro.status === 401) {
        localStorage.removeItem('token');
        router.navigate(['/login']);
      } else if (erro.status === 403) {
        const tokenAtual = localStorage.getItem('token');
        if (!tokenAtual || tokenEstaExpirado(tokenAtual)) {
          localStorage.removeItem('token');
          router.navigate(['/login']);
        } else {
          snackBar.open('Acesso negado. Você não tem permissão para realizar esta ação.', 'Fechar', {
            duration: 4000
          });
        }
      }
      return throwError(() => erro);
    })
  );
};
