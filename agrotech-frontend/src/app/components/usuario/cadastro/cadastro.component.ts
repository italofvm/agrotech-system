import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

import { UsuarioService } from '../../../services/usuario.service';
import { UserRoleModel } from '../../../models/user-role.model';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
  public tituloComp: string = 'Cadastro do usuário';
  public subtituloComp: string = 'Preencha os campos para criar uma conta';

  private usuarioService: UsuarioService = inject(UsuarioService);
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);

  public perfisDisponiveis = Object.values(UserRoleModel);

  cadastroFormulario = this.fb.group({
    login: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]],
    role: [UserRoleModel.USER, Validators.required]
  });

  aoCadastrar(): void {
    if (this.cadastroFormulario.valid) {
      this.usuarioService.cadastrar(this.cadastroFormulario.value as any).subscribe({
        next: () => this.router.navigate(['/login'])
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/login']);
  }
}