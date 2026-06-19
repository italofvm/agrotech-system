import { UserRoleModel } from './user-role.model';

export interface UsuarioModel {
  id: string;
  login: string;
  roles: UserRoleModel[];
}

export interface CadastroDTO {
  login: string;
  senha: string;
  role?: string;
}
