import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookDetailComponent } from './book-detail/book-detail.component';
import {FormsModule} from "@angular/forms";
// import { BookRecommendComponent } from './book-recommend/book-recommend.component';

@NgModule({
  declarations: [
    BookDetailComponent,
    // BookRecommendComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    BookDetailComponent,
    // BookRecommendComponent
  ]
})
export class BookModule { }
