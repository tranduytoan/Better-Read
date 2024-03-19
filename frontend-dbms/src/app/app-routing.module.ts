import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookDetailComponent} from "./book-detail/book-detail.component";
import {BookSearchComponent} from "./book-search/book-search.component";
import {LoginComponent} from "./login/login.component";
import {UserBooksComponent} from "./user-books/user-books.component";

const routes: Routes = [
  // { path: '', redirectTo: '/books', pathMatch: 'full' },
  {path: 'books', component: BookSearchComponent},
  { path: 'book/:id', component: BookDetailComponent },
  { path: 'login', component: LoginComponent },
  {path: 'userbooks/:id', component: UserBooksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
