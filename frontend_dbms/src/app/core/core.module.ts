import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './guards/auth.guard';
import { TokenInterceptor } from './interceptors/tokenInterceptor.service';
import { AuthService } from './services/auth.service';
import { BookService } from './services/book.service';
// import { BorrowService } from './services/borrow.service';
import { CartService } from './services/cart.service';
import { CategoryService } from './services/category.service';
import { ScrollService } from './services/scroll.service';
import { SearchService } from './services/search.service';
// import { UserBooksService } from './services/user-books.service';
import { UserService } from './services/user.service';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthService,
    BookService,
    // BorrowService,
    CartService,
    CategoryService,
    ScrollService,
    SearchService,
    // UserBooksService,
    UserService
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded');
    }
  }
}
