import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SensorModel } from '../models/sensor.model';
import { LeituraComSensorModel, SensorComLeituras } from '../models/leitura.model';

@Injectable({
  providedIn: 'root',
})
export class LeituraService {
  private http = inject(HttpClient);

  private readonly apiURL: string = 'http://localhost:8080/sensores';

  private readonly apiURLLeituras: string =
    'http://localhost:8080/leituras';

  constructor() { }

  obterDashboardCompleto(): Observable<SensorComLeituras[]> {
    return this.http.get<SensorModel[]>(this.apiURL).pipe(
      map(sensores => sensores.map(s => ({
        id: s.id,
        nome: s.nome,
        localizacaoAtual: s.localizacao,
        leituras: s.leituras ?? [],
      })))
    );
  }

  buscarTodas(): Observable<LeituraComSensorModel[]> {
    return this.http.get<LeituraComSensorModel[]>(this.apiURLLeituras);
  }

  registrarLeitura(leitura: any): Observable<void> {
    return this.http.post<void>(this.apiURLLeituras, leitura);
  }
}
