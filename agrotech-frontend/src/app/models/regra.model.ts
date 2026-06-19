export enum OperadorRegra {
  MAIOR_QUE = '>',
  MENOR_QUE = '<',
}

export enum TipoSensorRegra {
  TEMPERATURA = 'temperatura',
  UMIDADE_SOLO = 'umidade_solo',
  UMIDADE_AR = 'umidade_ar',
  LUMINOSIDADE = 'luminosidade',
}

export interface RegraModel {
  id: string;
  tipoSensor: TipoSensorRegra;
  operador: OperadorRegra;
  valor: number;
  ativo: boolean;
  mensagem: string;
}

export interface CriarRegraDTO {
  tipoSensor: TipoSensorRegra;
  operador: OperadorRegra;
  valor: number;
  mensagem: string;
}
