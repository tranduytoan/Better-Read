import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookDetailComponent } from './book-detail/book-detail.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppModule} from "../../app.module";
import {ReviewComponent} from "../review/review.component";
import {ReviewModule} from "../review/reviewmodule";
import {RouterLink} from "@angular/router";
import { BookRecommendationComponent } from './book-recommendation/book-recommendation.component';
// import { BookRecommendComponent } from './book-recommend/book-recommend.component';
@NgModule({
  declarations: [
    BookDetailComponent,
    BookRecommendationComponent,
    BookRecommendationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ReviewModule,
    RouterLink
  ],
  exports: [
    BookDetailComponent,
    BookRecommendationComponent
  ]
})
export class BookModule { }
