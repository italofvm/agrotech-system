import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegraModel, CriarRegraDTO } from '../models/regra.model';

@Injectable({
  providedIn: 'root',
})
export class RegraService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080/regras';

  buscarTodas(): Observable<RegraModel[]> {
    return this.http.get<RegraModel[]>(this.apiURL);
  }

  buscarPorId(id: string): Observable<RegraModel> {
    return this.http.get<RegraModel>(`${this.apiURL}/${id}`);
  }

  salvar(regra: CriarRegraDTO): Observable<void> {
    return this.http.post<void>(this.apiURL, regra);
  }

  ativar(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiURL}/${id}/ativar`, {});
  }

  desativar(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiURL}/${id}/desativar`, {});
  }
}
