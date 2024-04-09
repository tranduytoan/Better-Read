import { Component } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { catchError, throwError } from 'rxjs';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    this.authService.login(this.username, this.password)
      .subscribe(
        result => {
          console.log('Login successful');
          this.router.navigate(['/home']);
        },
        error => {
          // if (error.status === 401) {         401 is unauthorize error
            if (error.status == 500) {
            this.errorMessage = 'Invalid username or password';
          } else {
            this.errorMessage = 'An error occurred. Please try again later.';
          }
          console.error('Login error:', error);
        }
      );
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/home']);
    }
  }
}
