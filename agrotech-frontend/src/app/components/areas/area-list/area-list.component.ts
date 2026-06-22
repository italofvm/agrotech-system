import { Component, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { AreaService } from '../../../services/area.service';
import { AreaModel } from '../../../models/area.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UsuarioService } from '../../../services/usuario.service';
import { UserRoleModel } from '../../../models/user-role.model';
import { ConfirmDialogComponent } from '../../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-area-list',
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './area-list.component.html',
  styleUrl: './area-list.component.css',
})
export class AreaListComponent implements OnInit {
  private areaService = inject(AreaService);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);
  private usuarioService = inject(UsuarioService);

  areas: AreaModel[] = [];
  colunas = ['nome', 'descricao', 'status', 'acoes'];
  ehAdmin = computed(() => this.usuarioService.role() === UserRoleModel.ADMIN);

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

  toggleAtivo(area: AreaModel): void {
    const acao = area.ativo ? 'desativar' : 'ativar';
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        titulo: area.ativo ? 'Desativar Área' : 'Ativar Área',
        mensagem: `Deseja ${acao} a área "${area.nome}"?`,
        textoBotaoConfirmar: area.ativo ? 'Desativar' : 'Ativar',
      },
    });

    dialogRef.afterClosed().subscribe((confirmado: boolean) => {
      if (!confirmado) return;
      this.areaService.toggleAtivo(area.id).subscribe({
        next: () => {
          this.areas = this.areas.map(a =>
            a.id === area.id ? { ...a, ativo: !a.ativo } : a
          );
          this.snackBar.open(`Área ${acao === 'ativar' ? 'ativada' : 'desativada'} com sucesso.`, 'Fechar', { duration: 3000 });
        },
        error: () => this.snackBar.open(`Erro ao ${acao} área.`, 'Fechar', { duration: 3000 }),
      });
    });
  }
}
