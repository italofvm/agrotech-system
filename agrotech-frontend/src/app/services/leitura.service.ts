import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SensorComLeituras } from '../models/leitura.model';

@Injectable({
  providedIn: 'root',
})
export class LeituraService {
  private http = inject(HttpClient);

  private readonly apiURL: string = 'http://localhost:8080/api/sensores';

  private readonly apiURLLeituras: string =
    'http://localhost:8080/api/leituras';

  constructor() { }

  obterDashboardCompleto(): Observable<SensorComLeituras[]> {
    return this.http.get<SensorComLeituras[]>(`${this.apiURL}/com-leituras`);
  }

  registrarLeitura(leitura: any): Observable<void> {
    return this.http.post<void>(this.apiURLLeituras, leitura);
  }
}
