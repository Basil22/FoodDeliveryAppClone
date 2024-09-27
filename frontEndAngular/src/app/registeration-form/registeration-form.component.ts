import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { UserDTO } from '../models/userDTO';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-registeration-form',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './registeration-form.component.html',
  styleUrl: './registeration-form.component.css'
})
export class RegisterationFormComponent {
  userData: UserDTO = {
    userName: '',
    userEmail: '',
    userPhoneNumber: '',
    userPassword: '',
    userAddress: ''
  };

  

  constructor(private userService: UserService, private router: Router) {}

  register() {
    if (this.isFormValid()) {
      this.userService.register(this.userData).subscribe(
        (response: UserDTO) => {
          this.userData = response;
          console.log('Registration successful!', response);

          // Show popup
          window.alert('Register successful!');

          // Hide the popup and navigate after 3 seconds
          setTimeout(() => {
            
            this.router.navigate(['/']);  // Redirect to HomeComponent
          }, 3000);  // 3 seconds
        },
        (error: any) => {
          console.error('Registration failed', error);
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }

  isFormValid(): boolean {
    return true;  // Adjust validation logic as necessary
  }
}