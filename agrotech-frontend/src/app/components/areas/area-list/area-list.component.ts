import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { AreaService } from '../../../services/area.service';
import { AreaModel } from '../../../models/area.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-area-list',
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './area-list.component.html',
  styleUrl: './area-list.component.css',
})
export class AreaListComponent implements OnInit {
  private areaService = inject(AreaService);
  private snackBar = inject(MatSnackBar);

  areas: AreaModel[] = [];
  colunas = ['nome', 'descricao', 'acoes'];

  ngOnInit(): void {
    this.carregarAreas();
  }

  carregarAreas(): void {
    this.areaService.buscarTodas().subscribe({
      next: (dados) => (this.areas = dados),
      error: () => this.snackBar.open('Erro ao carregar áreas.', 'Fechar', { duration: 3000 }),
    });
  }

  deletar(id: string): void {
    this.areaService.deletar(id).subscribe({
      next: () => this.carregarAreas(),
      error: () => this.snackBar.open('Erro ao deletar área.', 'Fechar', { duration: 3000 }),
    });
  }
}
