import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MaterialModule } from '../../../shared/material/material.module';
import { AlertaService } from '../../../services/alerta.service';
import { AlertaModel, StatusAlerta } from '../../../models/alerta.model';

const PAGE_SIZE = 10;

@Component({
  selector: 'app-alerta-list',
  imports: [CommonModule, MaterialModule],
  templateUrl: './alerta-list.component.html',
  styleUrl: './alerta-list.component.css',
})
export class AlertaListComponent implements OnInit {
  private alertaService = inject(AlertaService);

  todosAlertas = signal<AlertaModel[]>([]);
  paginaAtual  = signal<number>(0);
  readonly pageSize = PAGE_SIZE;

  alertasPagina = computed(() => {
    const inicio = this.paginaAtual() * PAGE_SIZE;
    return this.todosAlertas().slice(inicio, inicio + PAGE_SIZE);
  });

  totalPaginas = computed(() =>
    Math.ceil(this.todosAlertas().length / PAGE_SIZE)
  );

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
      next: (dados) => {
        this.todosAlertas.set(dados);
        this.paginaAtual.set(0);
      },
      error: (erro) => console.error('Erro ao carregar alertas:', erro),
    });
  }

  filtrarPorStatus(status: string | null): void {
    if (status === null) {
      this.carregarAlertas();
      return;
    }
    this.alertaService.buscarPorStatus(status as StatusAlerta).subscribe({
      next: (dados) => {
        this.todosAlertas.set(dados);
        this.paginaAtual.set(0);
      },
      error: (erro) => console.error('Erro ao filtrar alertas:', erro),
    });
  }

  paginaAnterior(): void {
    if (this.paginaAtual() > 0) this.paginaAtual.update(p => p - 1);
  }

  proximaPagina(): void {
    if (this.paginaAtual() < this.totalPaginas() - 1) this.paginaAtual.update(p => p + 1);
  }

  toggleAlerta(alerta: AlertaModel, event: MatCheckboxChange): void {
    const novoStatus     = event.checked ? StatusAlerta.RESOLVIDO : StatusAlerta.ATIVO;
    const statusAnterior = alerta.status;

    this.todosAlertas.update(lista =>
      lista.map(a => a.id === alerta.id ? { ...a, status: novoStatus } : a)
    );

    const chamada = event.checked
      ? this.alertaService.resolver(alerta.id)
      : this.alertaService.reabrir(alerta.id);

    chamada.subscribe({
      error: () => {
        this.todosAlertas.update(lista =>
          lista.map(a => a.id === alerta.id ? { ...a, status: statusAnterior } : a)
        );
      }
    });
  }
}
