import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselComponent } from './components/carousel/carousel.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import {RouterLink} from "@angular/router";
import {SearchComponent} from "../features/search/search.component";
import {FormsModule} from "@angular/forms";
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import {MatDialogActions, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";

@NgModule({
  declarations: [
    CarouselComponent,
    FooterComponent,
    HeaderComponent,
    PaginationComponent,
    SearchComponent,
    ConfirmationDialogComponent
  ],
  imports: [
    CommonModule,
    RouterLink,
    FormsModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
  ],
  exports: [
    CarouselComponent,
    FooterComponent,
    HeaderComponent,
    PaginationComponent,
    SearchComponent
  ]
})
export class SharedModule { }
