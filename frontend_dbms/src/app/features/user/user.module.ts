import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule} from "@angular/forms";
import {RouterLink} from "@angular/router";
// import { UserBooksComponent } from './user-books/user-books.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegistrationComponent,
    // UserBooksComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        RouterLink
    ],
  exports: [
    LoginComponent,
    RegistrationComponent,
    // UserBooksComponent
  ]
})
export class UserModule { }
