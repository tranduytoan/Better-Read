import { Component } from '@angular/core';
import {User} from "../models/user";
import {UserService} from "../services/user.service";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {

  user: User = {
    username: '',
    email: '',
    password: '',
    role: 'USER',
  };
  errorMessage = '';


  constructor(private userService: UserService, private router: Router) {
  }

  onSubmit() {
    this.userService.registerUser(this.user)
      .subscribe(
        response => {
          // console.log('User registered successfully:', response);
          this.router.navigate(['/login']);

        },
        error => {
          this.errorMessage = 'Username or email already exists';
        }
      );
  }

}
