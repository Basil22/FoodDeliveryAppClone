import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
  loginData = {
    userPhoneNumber: '',
    userPassword: '',
  };
  errorMessage: string | null = null;

  constructor(private userService: UserService, private router: Router) {}
  login() {
    if (!this.loginData.userPhoneNumber || !this.loginData.userPassword) {
      alert('Please enter both phone number and password.');
    }
    // Ensure loginData contains phone number and password
    if (this.loginData.userPhoneNumber && this.loginData.userPassword) {
      this.userService.loginUser(this.loginData).subscribe({
        next: (response: any) => {
          console.log('Login successful!', response);
          console.log('User ID: ', response.userId);
          const successMessage = response.message || 'Login successful!';
          alert(successMessage);
        },
        error: (error: any) => {
          console.error('Login failed:', error);
          let errorMessage = 'Unknown error occurred';

          if (error.error) {
            const errorResponse = JSON.parse(error.error);
            errorMessage = errorResponse.message || errorMessage; // Get message from error response
          }

          alert(errorMessage);
        },
      });
    } else {
      this.errorMessage = 'Please provide both phone number and password.';
    }
  }
}
