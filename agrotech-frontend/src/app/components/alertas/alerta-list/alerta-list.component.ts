import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../shared/material/material.module';
import { AlertaService } from '../../../services/alerta.service';
import { AlertaModel, StatusAlerta } from '../../../models/alerta.model';

@Component({
  selector: 'app-alerta-list',
  imports: [CommonModule, MaterialModule],
  templateUrl: './alerta-list.component.html',
  styleUrl: './alerta-list.component.css',
})
export class AlertaListComponent implements OnInit {
  private alertaService = inject(AlertaService);

  alertas: AlertaModel[] = [];
  colunas = [
    'tipoSensor',
    'operador',
    'valorLimite',
    'valorLeitura',
    'dataHora',
    'status',
    'acoes',
  ];

  ngOnInit(): void {
    this.carregarAlertas();
  }

  carregarAlertas(): void {
    this.alertaService.buscarTodos().subscribe({
      next: (dados) => (this.alertas = dados),
      error: (erro) => console.error('Erro ao carregar alertas:', erro),
    });
  }

  filtrarPorStatus(status: string | null): void {
    if (status === null) {
      this.carregarAlertas();
      return;
    }
    this.alertaService.buscarPorStatus(status as StatusAlerta).subscribe({
      next: (dados) => (this.alertas = dados),
      error: (erro) => console.error('Erro ao filtrar alertas:', erro),
    });
  }

  resolver(id: string): void {
    this.alertaService.resolver(id).subscribe({
      next: () => this.carregarAlertas(),
      error: (erro) => console.error('Erro ao resolver alerta:', erro),
    });
  }
}
