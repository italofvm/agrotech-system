import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AreaModel, CriarAreaDTO } from '../models/area.model';

@Injectable({
  providedIn: 'root',
})
export class AreaService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080/areas';

  buscarTodas(): Observable<AreaModel[]> {
    return this.http.get<AreaModel[]>(this.apiURL);
  }

  buscarPorId(id: string): Observable<AreaModel> {
    return this.http.get<AreaModel>(`${this.apiURL}/${id}`);
  }

  salvar(area: CriarAreaDTO): Observable<void> {
    return this.http.post<void>(this.apiURL, area);
  }

  atualizar(id: string, area: CriarAreaDTO): Observable<void> {
    return this.http.put<void>(`${this.apiURL}/${id}`, area);
  }

  deletar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }

  toggleAtivo(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiURL}/${id}/toggle`, {});
  }
}
