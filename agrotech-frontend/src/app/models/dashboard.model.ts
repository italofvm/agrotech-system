export interface LeituraRecenteDTO {
  sensorNome: string;
  valor: number;
  dataHora: string;
}

export interface DashboardModel {
  totalSensores: number;
  totalAlertas: number;
  alertasAtivos: number;
  leiturasRecentes: LeituraRecenteDTO[];
}
