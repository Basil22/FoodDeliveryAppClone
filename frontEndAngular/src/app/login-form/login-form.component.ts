import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  userPhoneNumber: string = '';
  userPassword: string = '';

  constructor(private userService: UserService) {}

  login(): void {
    this.userService
      .loginUser(this.userPhoneNumber, this.userPassword)
      .subscribe({
        next: (response) => {
          console.log('Login successful:', response);
        },
        error: (error) => {
          console.error('Login failed: Register an Account', error);
        },
      });
  }
}
