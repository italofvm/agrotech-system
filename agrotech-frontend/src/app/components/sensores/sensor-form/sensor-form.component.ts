import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MaterialModule } from '../../material/material.module';
import { SensorService } from '../../services/sensor.service';

@Component({
  selector: 'app-sensor-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './sensor-form.component.html',
  styleUrls: ['./sensor-form.component.css']
})

export class SensorFormComponent {
  private fb = inject(FormBuilder);
  private sensorService = inject(SensorService);
  private router = inject(Router);

  public sensorForm = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(3)]],
    tipo: ['', Validators.required],
    localizacao: ['', Validators.required]
    ativo: [true]
  });

  aoSalvar(): void {
    if (this.sensorForm.valid) {
      const novoSensor = {
        nome: this.sensorForm.value.nome,
        tipo: this.sensorForm.value.tipo,
        localizacao: this.sensorForm.value.localizacao,
        ativo: !!this.sensorForm.value.ativo
      }

      this.sensorService.salvar(novoSensor as any).subscribe({
        next: () => this.router.navigate(['/sensores']),
        error: (err) => console.error('Erro ao salvar sensor:', err)
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/sensores']);
  }
}