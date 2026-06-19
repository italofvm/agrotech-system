import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { RegraService } from '../../../services/regra.service';
import { RegraModel } from '../../../models/regra.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-regra-list',
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './regra-list.component.html',
  styleUrl: './regra-list.component.css',
})
export class RegraListComponent implements OnInit {
  private regraService = inject(RegraService);
  private snackBar = inject(MatSnackBar);

  regras: RegraModel[] = [];
  colunas = ['tipoSensor', 'operador', 'valor', 'ativo', 'acoes'];

  ngOnInit(): void {
    this.carregarRegras();
  }

  carregarRegras(): void {
    this.regraService.buscarTodas().subscribe({
      next: (dados) => (this.regras = dados),
      error: () => this.snackBar.open('Erro ao carregar regras.', 'Fechar', { duration: 3000 }),
    });
  }

  ativar(id: string): void {
    this.regraService.ativar(id).subscribe({
      next: () => this.carregarRegras(),
      error: () => this.snackBar.open('Erro ao ativar regra.', 'Fechar', { duration: 3000 }),
    });
  }

  desativar(id: string): void {
    this.regraService.desativar(id).subscribe({
      next: () => this.carregarRegras(),
      error: () => this.snackBar.open('Erro ao desativar regra.', 'Fechar', { duration: 3000 }),
    });
  }
}
