import {Component, OnInit} from '@angular/core';
import {Book} from "../models/book";
import {BookService} from "../services/book.service";
import {max} from "rxjs";
import {ScrollService} from "../services/scroll.service";

@Component({
  selector: 'app-book-search',
  templateUrl: './book-search.component.html',
  styleUrls: ['./book-search.component.scss']
})
export class BookSearchComponent  implements OnInit{

  books: Book[] = [];
  totalPages: number = 20;
  maxVisiblePages: number = 5;
  currentPage: number = 1;
  searchQuery: string = '';
  pageSize = 12;


  constructor(private bookService: BookService, private scrollService: ScrollService) { }
  ngOnInit(): void {
    this.searchBooks(this.searchQuery, this.currentPage);
  }


   searchBooks(title: string, currentPage: number) {
    this.bookService.searchBooks(title, currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.books = response.books;
        this.totalPages = response.totalPages;
      },
      error: (error) => console.error('There was an error!', error),
    });
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.searchBooks(this.searchQuery, this.currentPage);
    this.scrollService.scrollToTop();
  }
}
