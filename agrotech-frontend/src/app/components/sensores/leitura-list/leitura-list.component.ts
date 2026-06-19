import { Component, DestroyRef, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { LeituraService } from '../../../services/leitura.service';
import { LeituraComSensorModel } from '../../../models/leitura.model';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormsModule } from '@angular/forms';

const PAGE_SIZE = 15;

@Component({
  selector: 'app-leitura-list',
  standalone: true,
  imports: [CommonModule, MaterialModule, FormsModule],
  templateUrl: './leitura-list.component.html',
  styleUrls: ['./leitura-list.component.css']
})
export class LeituraListComponent implements OnInit {
  private leituraService = inject(LeituraService);
  private destroyRef     = inject(DestroyRef);
  private router         = inject(Router);

  public todasLeituras  = signal<LeituraComSensorModel[]>([]);
  public carregando     = signal<boolean>(true);
  public erro           = signal<boolean>(false);
  public paginaAtual    = signal<number>(0);
  public filtroSensor   = signal<string>('');

  readonly pageSize = PAGE_SIZE;

  public leiturasFiltradas = computed(() => {
    const filtro = this.filtroSensor().toLowerCase().trim();
    const lista  = this.todasLeituras();
    return filtro
      ? lista.filter(l => l.sensorNome.toLowerCase().includes(filtro))
      : lista;
  });

  public totalPaginas = computed(() =>
    Math.ceil(this.leiturasFiltradas().length / PAGE_SIZE)
  );

  public leiturasPagina = computed(() => {
    const inicio = this.paginaAtual() * PAGE_SIZE;
    return this.leiturasFiltradas().slice(inicio, inicio + PAGE_SIZE);
  });

  ngOnInit(): void {
    this.erro.set(false);
    this.carregando.set(true);
    this.leituraService.buscarTodas()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: dados => {
          this.todasLeituras.set(dados);
          this.carregando.set(false);
        },
        error: () => {
          this.erro.set(true);
          this.carregando.set(false);
        }
      });
  }

  onFiltroChange(valor: string): void {
    this.filtroSensor.set(valor);
    this.paginaAtual.set(0);
  }

  paginaAnterior(): void {
    if (this.paginaAtual() > 0) this.paginaAtual.update(p => p - 1);
  }

  proximaPagina(): void {
    if (this.paginaAtual() < this.totalPaginas() - 1) this.paginaAtual.update(p => p + 1);
  }

  voltar(): void {
    this.router.navigate(['/telemetria/dashboard']);
  }
}
