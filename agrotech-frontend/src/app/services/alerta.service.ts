import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AlertaModel, StatusAlerta } from '../models/alerta.model';

@Injectable({
  providedIn: 'root',
})
export class AlertaService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080/alertas';

  buscarTodos(): Observable<AlertaModel[]> {
    return this.http.get<AlertaModel[]>(this.apiURL);
  }

  buscarPorStatus(status: StatusAlerta): Observable<AlertaModel[]> {
    return this.http.get<AlertaModel[]>(`${this.apiURL}?status=${status}`);
  }

  resolver(id: string): Observable<void> {
    return this.http.patch<void>(`${this.apiURL}/${id}/resolver`, {});
  }
}
