import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {
  userData = {
    userName: '',
    userEmail: '',
    userPhoneNumber: '',
    userPassword: '',
    userAddress: '',
  };

  constructor(private userService: UserService) {}

  register(): void {
    this.userService.registerUser(this.userData).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
      },
      error: (error) => {
        console.error('Registration failed', error);
      },
    });
  }
}
