import { Component, DestroyRef, computed, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../shared/material/material.module';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SensorService } from '../../../services/sensor.service';
import { LeituraService } from '../../../services/leitura.service';
import { SensorModel, TipoSensor } from '../../../models/sensor.model';
import { HttpErrorResponse } from '@angular/common/http';

const RANGES: Record<string, string> = {
  [TipoSensor.TEMPERATURA]:  '-10°C a 60°C',
  [TipoSensor.UMIDADE_SOLO]: '0% a 100%',
  [TipoSensor.UMIDADE_AR]:   '0% a 100%',
  [TipoSensor.LUMINOSIDADE]: '0 a 100.000 lux',
};

@Component({
  selector: 'app-sensor-leitura-form',
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './sensor-leitura-form.component.html',
  styleUrl: './sensor-leitura-form.component.css'
})

export class SensorLeituraFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private sensorService = inject(SensorService);
  private leituraService = inject(LeituraService);
  private snackBar = inject(MatSnackBar);
  private destroyRef = inject(DestroyRef);

  sensores = signal<SensorModel[]>([]);

  rangeHint = computed(() => {
    const id = this.telemetriaForm?.get('sensorId')?.value;
    const sensor = this.sensores().find(s => s.id === id);
    return sensor ? (RANGES[sensor.tipo] ?? null) : null;
  });

  telemetriaForm = this.fb.group({
    sensorId: ['', Validators.required],
    valor: [null, [Validators.required]],
    dataHora: [this.getHoraLocal(), Validators.required]
  });

  private getHoraLocal(): string {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    return now.toISOString().slice(0, 16);
  }

  ngOnInit(): void {
    this.sensorService.buscarTodos().pipe(takeUntilDestroyed(this.destroyRef)).subscribe(dados => this.sensores.set(dados));
  }

  enviarLeitura(): void {
    if (this.telemetriaForm.valid) {
      const payload = {
        sensorId: this.telemetriaForm.value.sensorId,
        valor: this.telemetriaForm.value.valor,
        dataHora: new Date(this.telemetriaForm.value.dataHora ?? '').toISOString()
      };

      this.leituraService.registrarLeitura(payload).subscribe({
        next: () => {
          this.snackBar.open('Leitura registrada com sucesso!', 'Fechar', { duration: 3000 });
          this.telemetriaForm.reset({ dataHora: this.getHoraLocal() });
        },
        error: (err: HttpErrorResponse) => {
          const mensagem = err.error?.erros?.[0] ?? 'Erro ao registrar leitura. Verifique o valor informado.';
          this.snackBar.open(mensagem, 'Fechar', { duration: 6000 });
        }
      });
    }
  }
}
