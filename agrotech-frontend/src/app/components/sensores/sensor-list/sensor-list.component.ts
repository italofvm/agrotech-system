import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MaterialModule } from '../../../shared/material/material.module';
import { SensorService } from '../../../services/sensor.service';
import { SensorModel } from '../../../models/sensor.model';
import { UsuarioService } from '../../../services/usuario.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserRoleModel } from '../../../models/user-role.model';

@Component({
  selector: 'app-sensor-list',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './sensor-list.component.html',
  styleUrl: './sensor-list.component.css'
})

export class SensorListComponent implements OnInit {
  tituloComp: string = 'Rede de Sensores';

  private sensorService = inject(SensorService);
  private roteador = inject(Router);
  private usuarioService = inject(UsuarioService);
  private destroyRef = inject(DestroyRef);
  private snackBar = inject(MatSnackBar);

  listaDeSensores = signal<SensorModel[]>([]);
  estaCarregando = signal<boolean>(false);
  ehAdmin = signal<boolean>(this.usuarioService.obterRole() === UserRoleModel.ADMIN);

  colunasTabela: string[] = ['nome', 'tipo', 'localizacao', 'status', 'acoes'];

  ngOnInit(): void {
    this.carregarDadosSensores();
  }

  carregarDadosSensores(): void {
    this.estaCarregando.set(true);
    this.sensorService.buscarTodos().pipe(takeUntilDestroyed(this.destroyRef)).subscribe({
      next: (dados: SensorModel[]) => {
        this.listaDeSensores.set(dados);
        this.estaCarregando.set(false);
      },
      error: (erro: Error) => {
        this.estaCarregando.set(false);
      }
    });
  }

  excluirSensor(id: string): void {
    if (confirm('Tem certeza que deseja excluir este sensor?')) {
      this.sensorService.deletar(id).subscribe({
        next: () => {
          this.carregarDadosSensores();
        },
        error: () => this.snackBar.open('Erro ao excluir sensor.', 'Fechar', { duration: 3000 })
      });
    }
  }

  alterarLocalizacao(sensor: SensorModel): void {
    const novaLoc = prompt(`Mover sensor "${sensor.nome}" para nova localização:`, sensor.localizacao);

    if (!novaLoc || novaLoc === sensor.localizacao) return;

    this.sensorService.atualizarLocalizacao(sensor.id, novaLoc).subscribe({
      next: () =>  this.carregarDadosSensores(),
      error: (err: any) => console.error('Erro ao atualizar localização:', err)
    });
  }

  navegarParaCadastro(): void {
    if (this.usuarioService.obterRole() !== UserRoleModel.ADMIN) {
      this.snackBar.open('Acesso negado. Apenas administradores podem cadastrar sensores.', 'Fechar', { duration: 4000 });
      return;
    }

    this.roteador.navigate(['/configuracoes/novo-sensor']);
  }

  verDetalhes(id: string): void {
    this.roteador.navigate(['/sensores/detalhes', id]);
  }
}
