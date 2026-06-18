import { LeituraModel } from './leitura.model';

export enum TipoSensor {
  TEMPERATURA = 'temperatura',
  UMIDADE_SOLO = 'umidade_solo',
  UMIDADE_AR = 'umidade_ar',
  LUMINOSIDADE = 'luminosidade',
}

export interface SensorLocalizacao {
  id: string;
  localizacao: string;
  dataInicio: string;
  dataFim: string;
}

export interface SensorModel {
  id: string;
  nome: string;
  localizacao: string;
  tipo: TipoSensor;
  ativo: boolean;
  leituras: LeituraModel[];
}
