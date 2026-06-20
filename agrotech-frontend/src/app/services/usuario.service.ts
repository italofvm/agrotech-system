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
  private _role = signal<string | null>(this.obterRole());
  public logado = computed(() => this._estaLogado());
  public role = computed(() => this._role());

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
          this._role.set(this.obterRole());
        })
      );
  }

  estaLogado(): boolean {
    return this._estaLogado();
  }

  private verificarToken(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      if (decoded.exp && decoded.exp * 1000 < Date.now()) {
        localStorage.removeItem('token');
        return false;
      }
      return true;
    } catch {
      localStorage.removeItem('token');
      return false;
    }
  }

  obterToken(): string | null {
    return localStorage.getItem('token');
  }

  obterRole(): string | null {
    const token = this.obterToken();
    if (!token) return null;

    try {
      const decode: any = jwtDecode(token);
      if (decode.exp && decode.exp * 1000 < Date.now()) {
        return null;
      }
      return decode.role || null;
    } catch {
      return null;
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    this._estaLogado.set(false);
    this._role.set(null);
  }
}
