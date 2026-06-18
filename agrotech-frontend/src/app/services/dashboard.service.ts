import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardModel } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private http = inject(HttpClient);
  private readonly apiURL = 'http://localhost:8080/dashboard';

  buscarDados(): Observable<DashboardModel> {
    return this.http.get<DashboardModel>(this.apiURL);
  }
}
