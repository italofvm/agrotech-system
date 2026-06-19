import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from "@angular/common";
import { MaterialModule } from '../../../shared/material/material.module';
import { LeituraService } from '../../../services/leitura.service';
import { SensorComLeituras } from '../../../models/leitura.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {
  private leituraService = new LeituraService();

  public listaSensoresComLeituras = signal<SensorComLeituras[]>([]);
  public carregando = signal<boolean>(true);

  ngOnInit(): void {
    this.carregarDados();
}

  carregarDados(): void {
    this.carregando.set(true);
    this.leituraService.obterDashboardCompleto().subscribe({
      next: (dados: any[]) => {
        console.log('JSON do Backend:', JSON.stringify(dados, null, 2));

          dados.forEach(s => {
            console.log(`Sensor "${s.nome}" | Localização atual: "${s.localizacaoAtual}"`);
          })

        this.listaSensoresComLeituras.set(dados);
        this.carregando.set(false);
      },

      error: (err: any) => {
        console.error('Erro ao carregar dados do dashboard:', err);
        this.carregando.set(false);
      }
    });
  }
}
