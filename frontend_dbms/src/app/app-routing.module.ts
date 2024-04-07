import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SignUpComponent} from "./sign-up/sign-up.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./auth.guard";
import {RegistrationComponent} from "./registration/registration.component";
import {MainContentComponent} from "./main-content/main-content.component";
import {BookDetailComponent} from "./features/book-detail/book-detail.component";
import {LandingPageComponent} from "./landingPage/landing-page.component";
import {CartComponent} from "./cart/cart.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'register', component: RegistrationComponent},
  { path: 'book/:id', component: BookDetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cart', component: CartComponent },
  // {path: 'userbooks/:id', component: UserBooksComponent},
  { path: 'main-content', component: MainContentComponent},
  { path: 'home', component: LandingPageComponent, canActivate: [AuthGuard]}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
