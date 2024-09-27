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
        next: (response: UserDTO) => {
          this.userData = response;
          alert(response);
        },
        error: (error) => {
          console.log(error);
        },
      });
    } else {
      console.log('Form is invalid');
    }
  }

  isFormValid(): boolean {
    return true; // Adjust validation logic as necessary
  }
}
