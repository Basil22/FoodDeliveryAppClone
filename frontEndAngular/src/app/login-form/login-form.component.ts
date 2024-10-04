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
 
    if (this.loginData.userPhoneNumber && this.loginData.userPassword) {
      this.userService.loginUser(this.loginData).subscribe({
        next: (response: any) => {
          console.log('Login successful!', response);
         
          // Store the user ID or token in localStorage
          localStorage.setItem('userId', response.userId); // Assuming the response contains a userId or token
         
          const successMessage = response.message || 'Login successful!';
          alert(successMessage);
 
          setTimeout(() => {
            this.router.navigate(['/']);  // Redirect to HomeComponent
          }, 3000);  
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