import { UserRoleModel } from './user-role.model';
export interface UsuarioModel {
  id: string;
  login: string;
  senha: string;
  roles: UserRoleModel[];
}
