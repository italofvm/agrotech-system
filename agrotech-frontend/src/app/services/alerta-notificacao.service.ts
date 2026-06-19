import { Injectable, inject, signal } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

interface AlertaSse {
  tipoSensor: string;
  operador: string;
  valorLeitura: number;
  valorLimite: number;
  mensagem: string;
}

@Injectable({ providedIn: 'root' })
export class AlertaNotificacaoService {
  private snackBar = inject(MatSnackBar);
  private router   = inject(Router);

  private source: EventSource | null = null;

  private readonly _totalNovos = signal<number>(0);
  readonly totalNovos = this._totalNovos.asReadonly();

  conectar(): void {
    // Evita abrir múltiplas conexões
    if (this.source?.readyState === EventSource.OPEN) return;

    this.source = new EventSource('http://localhost:8080/alertas/stream');

    this.source.onmessage = (event) => {
      try {
        const alerta: AlertaSse = JSON.parse(event.data);
        this._totalNovos.update(n => n + 1);
        this.exibirNotificacao(alerta);
      } catch { /* ignora evento malformado */ }
    };

    // EventSource reconecta automaticamente em caso de erro de rede
    this.source.onerror = () => {
      if (this.source?.readyState === EventSource.CLOSED) {
        this.source = null;
      }
    };
  }

  desconectar(): void {
    this.source?.close();
    this.source = null;
    this._totalNovos.set(0);
  }

  /** Chame ao entrar na tela de alertas para zerar o badge */
  reconhecer(): void {
    this._totalNovos.set(0);
  }

  private exibirNotificacao(alerta: AlertaSse): void {
    const operadorLabel = alerta.operador === '>' ? 'acima de' : 'abaixo de';
    const mensagem = `⚠️ ${alerta.mensagem}`;
    const detalhe  = `${alerta.tipoSensor}: ${alerta.valorLeitura} ${operadorLabel} ${alerta.valorLimite}`;

    this.snackBar
      .open(`${mensagem} — ${detalhe}`, 'Ver Alertas', {
        duration: 8000,
        panelClass: ['snack-alerta'],
      })
      .onAction()
      .subscribe(() => this.router.navigate(['/alertas']));
  }
}
