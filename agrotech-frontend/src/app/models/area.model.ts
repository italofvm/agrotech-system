export interface AreaModel {
  id: string;
  nome: string;
  descricao?: string;
}

export interface CriarAreaDTO {
  nome: string;
  descricao?: string;
}
