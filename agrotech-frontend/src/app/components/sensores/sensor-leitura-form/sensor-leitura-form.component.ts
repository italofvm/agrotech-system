import { Component, Inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../shared/material/material.module';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SensorService } from '../../../services/sensor.service';
import { LeituraService } from '../../../services/leitura.service';
import { SensorModel } from '../../../models/sensor.model';

@Component({
  selector: 'app-sensor-leitura-form',
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './sensor-leitura-form.component.html',
  styleUrl: './sensor-leitura-form.component.css'
})

export class SensorLeituraFormComponent implements OnInit {
  private fb = Inject(FormBuilder);
  private sensorService = new SensorService();
  private leituraService = new LeituraService();
  private snackBar = Inject(MatSnackBar);

  sensores = signal<SensorModel[]>([]);

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
    this.sensorService.buscarTodos().subscribe(dados => this.sensores.set(dados));
  }

  enviarLeitura(): void {
    if (this.telemetriaForm.valid) {
      const payload = {
        sensorId: this.telemetriaForm.value.sensorId,
        valor: this.telemetriaForm.value.valor,
        dataHora: new Date(this.telemetriaForm.value.dataHora).toISOString()
      };

      console.log('Payload enviado:', payload);

      this.leituraService.registrarLeitura(payload).subscribe({
        next: () => {
          alert('Leitura registrada com sucesso!');
          this.telemetriaForm.reset({
            dataHora: this.getHoraLocal()
          });
        },
        error: (err) => {
          console.error('Erro ao registrar leitura:', err);
        }
      });
    }
  }
}
