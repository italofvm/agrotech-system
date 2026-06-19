import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  senhaVisivel = false;

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private usuarioService = inject(UsuarioService);
  private snackBar = inject(MatSnackBar);

  public loginFormulario = this.fb.nonNullable.group({
    login: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]]
  });

  aoSubmeter(): void {
    if (this.loginFormulario.invalid) {
      this.loginFormulario.markAllAsTouched();
      return;
    }

    this.usuarioService.login(this.loginFormulario.getRawValue()).subscribe({
      next: () => {
        this.router.navigate(['/telemetria/dashboard']);
      },
      error: (erro) => {
        const mensagem = erro.status === 401
          ? 'E-mail ou senha inválidos.'
          : 'Erro ao conectar com o servidor. Tente novamente.';
        this.snackBar.open(mensagem, 'Fechar', { duration: 4000 });
      }
    });
  }
}
