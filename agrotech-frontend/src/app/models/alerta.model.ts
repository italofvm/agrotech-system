export enum StatusAlerta {
  ATIVO = 'ATIVO',
  RESOLVIDO = 'RESOLVIDO',
}

export interface AlertaModel {
  id: string;
  status: StatusAlerta;
  dataHora: string;
  valorLeitura: number;
  tipoSensor: string;
  operador: string;
  valorLimite: number;
}
