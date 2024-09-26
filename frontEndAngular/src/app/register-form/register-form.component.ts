import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { FormsModule } from '@angular/forms';
import { UserDTO } from '../models/userDTO';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
})
export class RegisterFormComponent {
  user: UserDTO = {} as UserDTO;

  constructor(private userService: UserService) {}

  register(): void {
    this.userService.registerUser(this.user).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
      },
      error: (error) => {
        console.error('Registration failed', error);
      },
    });
  }

  //spring boot controller check method

  getAllUser(): void {
    this.userService.getUser().subscribe({
      next: (response) => {
        console.log(response);
      },
    });
  }
}
