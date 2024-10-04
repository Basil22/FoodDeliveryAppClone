import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { UserDTO } from '../models/userDTO';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-registeration-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './registeration-form.component.html',
  styleUrl: './registeration-form.component.css',
})
export class RegisterationFormComponent {
  userData: UserDTO = {
    userName: '',
    userEmail: '',
    userPhoneNumber: '',
    userPassword: '',
    userAddress: '',
  };

  constructor(private userService: UserService, private router: Router) {}

  register() {
    if (this.isFormValid()) {
      this.userService.register(this.userData).subscribe({
        next: (response: any) => {
          // Assuming the backend returns {message: 'Registration successful', userId: <ID>}
          const successMessage = response.message || 'Registration successful';
          alert(successMessage); // Display the success message
          console.log('User ID:', response.userId); // Optional: Log or store the userId

          // Clear the form after successful registration
          this.clearForm();
          setTimeout(() => {
            this.router.navigate(['/']);  // Redirect to HomeComponent
          }, 3000);  
        },
        error: (error) => {
          let errorMessage = 'Unknown error occurred';
          if (error.error) {
            errorMessage = error.error.message || errorMessage; // Get the error message from backend response
          }
          alert(errorMessage); // Display the error message
        },
      });
    } else {
      console.log('Form is invalid');
    }
  }

  clearForm() {
    // Reset the form fields
    this.userData = {
      userName: '',
      userEmail: '',
      userPhoneNumber: '',
      userPassword: '',
      userAddress: '',
    };
  }

  isFormValid(): boolean {
    if (
      !this.userData.userName ||
      !this.userData.userEmail ||
      !this.userData.userPhoneNumber ||
      !this.userData.userPassword ||
      !this.userData.userAddress
    ) {
      alert('Please fill all the fields.');
      return false;
    }
    return true; // Adjust validation logic as necessary
  }
}
