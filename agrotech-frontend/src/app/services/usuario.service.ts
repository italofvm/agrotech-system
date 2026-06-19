import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal, computed } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

import { UsuarioModel } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080';
  private _estaLogado = signal<boolean>(this.verificarToken());
  public estaLogado = computed(() => this._estaLogado());

  constructor() {}

  cadastrar(usuario: UsuarioModel): Observable<void> {
    return this.http.post<void>(`${this.apiURL}/usuarios`, usuario);
  }

  login(credenciais: Partial<UsuarioModel>): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiURL}/auth/login`, credenciais)
      .pipe(
        tap(resposta => {
          localStorage.setItem('token', resposta.token);

          this._estaLogado.set(true);
        })
      );
  }

  private verificarToken(): boolean {
    return !!localStorage.getItem('token');
  }

  obterToken(): string | null {
    return localStorage.getItem('token');
  }

  obterRole(): string | null {
    const token = this.obterToken();
    if (!token) return null;
    
    const decode: any = jwtDecode(token);

    return decode.roles || null;
  }

  logout(): void {
    localStorage.removeItem('token');
    this._estaLogado.set(false);
  }
}
