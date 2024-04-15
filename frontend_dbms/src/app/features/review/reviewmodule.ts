import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewComponent } from './review.component';
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [ReviewComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  exports: [ReviewComponent]
})
export class ReviewModule { }
