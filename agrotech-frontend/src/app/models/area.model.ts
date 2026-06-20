export interface AreaModel {
  id: string;
  nome: string;
  descricao?: string;
  ativo: boolean;
}

export interface CriarAreaDTO {
  nome: string;
  descricao?: string;
}
