import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';

import { UsuarioService } from '../../../services/usuario.service';
import { UserRoleModel } from '../../../models/user-role.model';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
  ],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
  public tituloComp: string = 'Cadastro do usuário';
  public subtituloComp: string = 'Preencha os campos para criar uma conta';

  public perfisDisponiveis = Object.values(UserRoleModel);
  public senhaVisivel = false;

  private usuarioService = inject(UsuarioService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  cadastroFormulario = this.fb.group({
    login: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]],
    role: [UserRoleModel.USER, Validators.required]
  });

  aoCadastrar(): void {
    if (this.cadastroFormulario.invalid) {
      this.cadastroFormulario.markAllAsTouched();
      return;
    }

    this.usuarioService.cadastrar(this.cadastroFormulario.value as any).subscribe({
      next: () => {
        this.snackBar.open('Conta criada com sucesso!', 'Fechar', { duration: 3000 });
        this.router.navigate(['/login']);
      },
      error: (erro) => {
        const mensagem = erro.status === 409
          ? 'Este e-mail já está cadastrado.'
          : 'Erro ao criar conta. Verifique sua conexão e tente novamente.';
        this.snackBar.open(mensagem, 'Fechar', { duration: 4000 });
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/login']);
  }
}
