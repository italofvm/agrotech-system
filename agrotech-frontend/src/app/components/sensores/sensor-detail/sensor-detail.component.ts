import { Component, OnInit, Inject, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MaterialModule } from '../../material/material.module';
import { SensorService } from '../../services/sensor.service';
import { SensorModel } from '../../models/sensor.model';

@Component({
  selector: 'app-sensor-detail',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './sensor-detail.component.html',
  styleUrls: ['./sensor-detail.component.css']
})

export class SensorDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private sensorService = inject(SensorService);

  public sensor = signal<SensorModel | null>(null);
  public carregando = signal<boolean>(true);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.carregarDetalhes(id);
    }
  }

  carregarDetalhes(id: string): void {
    this.carregando.set(true);
    this.sensorService.buscarPorId(id).subscribe({
      next: (dados) => {
        this.sensor.set(dados);
        this.carregando.set(false);
      },
      
      error: (erro) => {
        console.error('Erro ao carregar detalhes do sensor:', erro);
        this.carregando.set(false);
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/sensores']);
  }
}