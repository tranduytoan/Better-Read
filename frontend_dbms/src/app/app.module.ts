import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import {HttpClientModule} from "@angular/common/http";
import { PaginationComponent } from './pagination/pagination.component';
import { BookDetailComponent } from './features/book-detail/book-detail.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import {JwtModule} from "@auth0/angular-jwt";
import { HomeComponent } from './home/home.component';
import { CatergoryListComponent } from './category-list/catergory-list.component';
import { RegistrationComponent } from './registration/registration.component';
import { SearchComponent } from './features/search/search.component';
import { MainContentComponent } from './main-content/main-content.component';
import { CarouselComponent } from './carousel/carousel.component';
import { LandingPageComponent } from './landingPage/landing-page.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './services/tokenInterceptor.service';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { CartComponent } from './cart/cart.component';
export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PaginationComponent,
    BookDetailComponent,
    FooterComponent,
    LoginComponent,
    SignUpComponent,
    HomeComponent,
    CatergoryListComponent,
    RegistrationComponent,
    SearchComponent,
    MainContentComponent,
    CarouselComponent,
    LandingPageComponent,
    CartComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['localhost:8080'],
        disallowedRoutes: []
      }
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
