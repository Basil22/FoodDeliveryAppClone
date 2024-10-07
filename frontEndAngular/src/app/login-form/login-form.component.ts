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
        next: (response) => {
          console.log('Login successful!', response);
         // console.log('Full Response:', JSON.stringify(response));
         
          // Store the user ID or token in localStorage
          if (response && response.userId !== undefined) {
            localStorage.setItem('userId', response.userId.toString());
            const successMessage = response.message || 'Login successful!';
          alert(successMessage);
 
          setTimeout(() => {
            this.router.navigate(['/']);  // Redirect to HomeComponent
          }, 3000);   // Store userId as a string
          } else {
            console.error('UserId not found in response:', response);
          }
         
          
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