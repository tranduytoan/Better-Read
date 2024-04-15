import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { JwtModule } from "@auth0/angular-jwt";
import { HomeComponent } from './pages/home/home.component';
import { MainContentComponent } from './pages/main-content/main-content.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { CartComponent } from './features/cart/cart.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { BookModule } from './features/book/book.module';
import { UserModule } from './features/user/user.module';
import { ReadingProgressListComponent } from './features/readingprogresslist/readingprogresslist.component';
// import { CategoryModule } from './features/category/category.module';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainContentComponent,
    CartComponent,
    ReadingProgressListComponent,
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
    }),
    CoreModule,
    SharedModule,
    BookModule,
    UserModule,
    ReactiveFormsModule,
    // CategoryModule
  ],
  providers: [],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

