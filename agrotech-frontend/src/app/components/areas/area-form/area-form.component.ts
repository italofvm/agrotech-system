import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { AreaService } from '../../../services/area.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-area-form',
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './area-form.component.html',
  styleUrl: './area-form.component.css',
})
export class AreaFormComponent implements OnInit {
  private areaService = inject(AreaService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private snackBar = inject(MatSnackBar);

  tituloComp = 'Nova Área';
  subtituloComp = 'Cadastre uma nova área agrícola';
  modoEdicao = false;
  areaId: string | null = null;

  areaFormulario = this.fb.group({
    nome: ['', Validators.required],
    descricao: [''],
  });

  ngOnInit(): void {
    this.areaId = this.route.snapshot.paramMap.get('id');
    if (this.areaId) {
      this.modoEdicao = true;
      this.tituloComp = 'Editar Área';
      this.subtituloComp = 'Atualize os dados da área';

      this.areaService.buscarPorId(this.areaId).subscribe({
        next: (area) => this.areaFormulario.patchValue(area),
        error: () => this.snackBar.open('Erro ao carregar área.', 'Fechar', { duration: 3000 }),
      });
    }
  }

  aoSubmeter(): void {
    if (this.areaFormulario.valid) {
      const dados = this.areaFormulario.value as any;

      const operacao = this.modoEdicao
        ? this.areaService.atualizar(this.areaId!, dados)
        : this.areaService.salvar(dados);

      operacao.subscribe({
        next: () => this.router.navigate(['/areas']),
        error: () => this.snackBar.open('Erro ao salvar área. Tente novamente.', 'Fechar', { duration: 3000 }),
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/areas']);
  }
}
