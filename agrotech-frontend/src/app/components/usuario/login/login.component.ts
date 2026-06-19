import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  public tituloComp = 'Login';
  public subtituloComp = 'Informe seus dados para acessar o sistema';

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private usuarioService = inject(UsuarioService);

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
      next: (resposta) => {
        console.log('Resposta do login:', resposta);
        console.log('Token salvo:', localStorage.getItem('token'));

        this.router.navigate(['/sensores']);
      },
      error: (erro) => {
        console.error('Erro ao fazer login:', erro);
        console.error('Status:', erro.status);
        console.error('Body:', erro.error);
      }
    });
  }
}