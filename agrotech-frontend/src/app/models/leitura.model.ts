export interface LeituraModel {
  id?: string;
  valor: number;
  dataHora: string;
  localizacao: string;
}

export interface SensorComLeituras {
  id: string;
  nome: string;
  localizacaoAtual: string;
  leituras: LeituraModel[];
  historico?: any[];
}
