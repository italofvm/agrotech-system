import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorModel } from '../models/sensor.model';

@Injectable({
  providedIn: 'root',
})
export class SensorService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080/sensores';

  constructor() { }

  buscarTodos(): Observable<SensorModel[]> {
    return this.http.get<SensorModel[]>(`${this.apiURL}`);
  }

  buscarPorId(id: string): Observable<SensorModel> {
    return this.http.get<SensorModel>(`${this.apiURL}/${id}`);
  }

  salvar(sensor: Partial<SensorModel>) {
    return this.http.post<void>(`${this.apiURL}`, sensor);
  }

  atualizarNome(id: string, nome: string): Observable<void> {
    return this.http.put<void>(`${this.apiURL}/${id}`, { nome });
  }

  atualizarLocalizacao(id: string, localizacao: string): Observable<void> {
    return this.http.put<void>(`${this.apiURL}/${id}`, { localizacao });
  }

  deletar(id: string) {
    return this.http.delete<void>(`${this.apiURL}/${id}`);
  }

  buscarComLeituras(id: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/com-leituras`);
  }
}
