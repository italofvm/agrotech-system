import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { RegraService } from '../../../services/regra.service';
import { OperadorRegra, TipoSensorRegra } from '../../../models/regra.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-regra-form',
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './regra-form.component.html',
  styleUrl: './regra-form.component.css',
})
export class RegraFormComponent {
  private regraService = inject(RegraService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  tituloComp = 'Nova Regra';
  subtituloComp = 'Cadastre uma regra de alerta';

  tiposSensor = Object.values(TipoSensorRegra);
  operadores = Object.values(OperadorRegra);

  regraFormulario = this.fb.group({
    tipoSensor: ['', Validators.required],
    operador: ['', Validators.required],
    valor: [null, [Validators.required, Validators.min(0.01)]],
    mensagem: ['', Validators.required],
  });

  aoSubmeter(): void {
    if (this.regraFormulario.valid) {
      this.regraService.salvar(this.regraFormulario.value as any).subscribe({
        next: () => this.router.navigate(['/regras']),
        error: () => this.snackBar.open('Erro ao salvar regra. Tente novamente.', 'Fechar', { duration: 3000 }),
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/regras']);
  }
}
