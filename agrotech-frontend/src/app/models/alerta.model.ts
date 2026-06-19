export enum StatusAlerta {
  ATIVO = 'ativo',
  RESOLVIDO = 'resolvido',
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
