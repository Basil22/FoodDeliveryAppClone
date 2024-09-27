import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})

  export class LoginFormComponent {
    loginData = {
      userPhoneNumber: '',
      userPassword: ''
    };
    errorMessage: string | null = null;
  
    constructor(private userService: UserService, private router: Router) {}
    login() {
      // Ensure loginData contains phone number and password
      if (this.loginData.userPhoneNumber && this.loginData.userPassword) {
        this.userService.loginUser(this.loginData).subscribe(
          (response: any) => {
            console.log('Login successful!', response);
            
            // Assuming the backend returns a token or user data upon successful login
            // Save the token if needed (for JWT authentication)
            if (response.token) {
              localStorage.setItem('authToken', response.token);
            }
            window.alert('Login successful!');
            setTimeout(() => {
              
              this.router.navigate(['/']);  // Redirect to HomeComponent
            }, 3000);  // 3 seconds
          },
          (error: any) => {
            console.error('Login failed', error);
    
            // Handle different error statuses (e.g., 401 Unauthorized)
            if (error.status === 401) {
              this.errorMessage = 'Unauthorized. Please check your phone number and password.';
            } else {
              this.errorMessage = error?.error?.message || 'Login failed. Please try again.';
            }
          }
        );
      } else {
        this.errorMessage = 'Please provide both phone number and password.';
      }
    }
  }    
    