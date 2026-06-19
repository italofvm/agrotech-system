import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

import { UsuarioModel, CadastroDTO } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080';

  private _estaLogado = signal<boolean>(this.verificarToken());
  public logado = computed(() => this._estaLogado());

  constructor() {}

  cadastrar(usuario: CadastroDTO): Observable<void> {
    return this.http.post<void>(`${this.apiURL}/cadastro`, usuario);
  }

  login(credenciais: { login: string; senha: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiURL}/auth/login`, credenciais)
      .pipe(
        tap(resposta => {
          localStorage.setItem('token', resposta.token);
          this._estaLogado.set(true);
        })
      );
  }

  estaLogado(): boolean {
    return this._estaLogado();
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

    try {
      const decode: any = jwtDecode(token);
      return decode.role || null;
    } catch {
      this.logout();
      return null;
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    this._estaLogado.set(false);
  }
}
