import { Component, DestroyRef, OnInit, computed, inject, signal } from '@angular/core';
import { CommonModule } from "@angular/common";
import { Router } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { LeituraService } from '../../../services/leitura.service';
import { AlertaService } from '../../../services/alerta.service';
import { SensorComLeituras } from '../../../models/leitura.model';
import { AlertaModel, StatusAlerta } from '../../../models/alerta.model';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { interval, forkJoin } from 'rxjs';
import { switchMap, startWith } from 'rxjs/operators';

const INTERVALO_POLLING_MS = 10_000;

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private leituraService = inject(LeituraService);
  private alertaService  = inject(AlertaService);
  private destroyRef     = inject(DestroyRef);
  private router         = inject(Router);

  public listaSensoresComLeituras = signal<SensorComLeituras[]>([]);
  public alertasAtivos            = signal<AlertaModel[]>([]);
  public carregando               = signal<boolean>(true);
  public ultimaAtualizacao        = signal<Date | null>(null);

  // KPI calculados
  totalSensores  = computed(() => this.listaSensoresComLeituras().length);
  totalLeituras  = computed(() =>
    this.listaSensoresComLeituras().reduce((acc, s) => acc + s.leituras.length, 0)
  );
  sensoresAtivos = computed(() =>
    this.listaSensoresComLeituras().filter(s => s.leituras.length > 0).length
  );
  totalAlertasAtivos = computed(() => this.alertasAtivos().length);

  ngOnInit(): void {
    interval(INTERVALO_POLLING_MS)
      .pipe(
        startWith(0),
        switchMap(() => forkJoin({
          leituras: this.leituraService.obterDashboardCompleto(),
          alertas:  this.alertaService.buscarTodos(),
        })),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: ({ leituras, alertas }) => {
          this.listaSensoresComLeituras.set(leituras);
          this.alertasAtivos.set(
            alertas.filter(a => a.status === StatusAlerta.ATIVO)
          );
          this.ultimaAtualizacao.set(new Date());
          this.carregando.set(false);
        },
        error: () => {
          this.carregando.set(false);
        }
      });
  }

  carregarDados(): void {
    this.carregando.set(true);
    forkJoin({
      leituras: this.leituraService.obterDashboardCompleto(),
      alertas:  this.alertaService.buscarTodos(),
    })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ leituras, alertas }) => {
          this.listaSensoresComLeituras.set(leituras);
          this.alertasAtivos.set(
            alertas.filter(a => a.status === StatusAlerta.ATIVO)
          );
          this.ultimaAtualizacao.set(new Date());
          this.carregando.set(false);
        },
        error: () => {
          this.carregando.set(false);
        }
      });
  }

  verHistoricoCompleto(): void {
    this.router.navigate(['/telemetria/leituras']);
  }
}
