import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./features/user/login/login.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {RegistrationComponent} from "./features/user/registration/registration.component";
import {MainContentComponent} from "./main-content/main-content.component";
import {BookDetailComponent} from "./features/book/book-detail/book-detail.component";
import {CartComponent} from "./features/cart/cart.component";

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'register', component: RegistrationComponent},
  { path: 'book/:id', component: BookDetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cart', component: CartComponent },
  // {path: 'userbooks/:id', component: UserBooksComponent},
  { path: 'main-content', component: MainContentComponent},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
