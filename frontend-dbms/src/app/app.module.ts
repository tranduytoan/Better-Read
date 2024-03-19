import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookSearchComponent } from './book-search/book-search.component';
import { FooterComponent } from './footer/footer.component';
import {FormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { BookDetailComponent } from './book-detail/book-detail.component';
import {RouterModule} from "@angular/router";
import { PaginationComponent } from './pagination/pagination.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { UserBooksComponent } from './user-books/user-books.component';

@NgModule({
  declarations: [
    AppComponent,
    BookSearchComponent,
    FooterComponent,
    BookDetailComponent,
    PaginationComponent,
    HeaderComponent,
    LoginComponent,
    SignUpComponent,
    UserBooksComponent,
  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
