import {Component, OnInit} from '@angular/core';
import {Book} from "../models/book";
import {BookService} from "../services/book.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent implements OnInit{
  book: Book | undefined

    constructor(
      private route: ActivatedRoute,
      private bookService: BookService
    ) { }
    ngOnInit(): void {
      this.getBookDetail();
      // console.log('BookDetailComponent initialized');
    }

   // getBookDetail() {
   //  const id = this.route.snapshot.paramMap.get('id');
   //  if (id) {
   //    this.bookService.getBookDetail(id).subscribe({
   //      next: (response) => {
   //        this.book = response;
   //      },
   //      error: (error) => console.error('There was an error', error),
   //    });
   //  }
  getBookDetail() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.bookService.getBookDetail(id).subscribe({
        next: (response) => {
          console.log('Book data:', response);
          this.book = response;
        },
        error: (error) => console.error('There was an error', error),
      });
    }
  }
}
