import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import {ToastrService} from "ngx-toastr";
import {delay} from "rxjs";

@Component({
  selector: 'app-signup',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  username = '';
  email = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastrService) {}

  onSubmit() {
    this.authService.signup(this.username, this.email, this.password).pipe(delay(10000)).subscribe(
      result => {
        this.toastr.success('Signup successful');
        // this.router.navigate(['/login']);

      },
      error => {
        this.toastr.error('Username or email already exists');
      }
    );
  }
}
