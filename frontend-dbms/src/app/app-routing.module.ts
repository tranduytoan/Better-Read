import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookDetailComponent} from "./book-detail/book-detail.component";
import {BookSearchComponent} from "./book-search/book-search.component";

const routes: Routes = [
  { path: '', redirectTo: '/book', pathMatch: 'full' },
  {path: 'book', component: BookSearchComponent},
  { path: 'book/:id', component: BookDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
