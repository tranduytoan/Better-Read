import {Component, OnInit} from '@angular/core';
import {Book} from "../models/book";
import {BookService} from "../services/book.service";

@Component({
  selector: 'app-book-search',
  templateUrl: './book-search.component.html',
  styleUrl: './book-search.component.scss'
})
export class BookSearchComponent  implements OnInit{

  books: Book[] = [];
  totalPages: number = 0;
  currentPage: number = 0;
  searchQuery: string = '';

  constructor(private bookService: BookService) { }
  ngOnInit(): void {
    this.searchBooks(this.searchQuery, this.currentPage);
  }


  public searchBooks(title: string, currentPage: number) {
    this.bookService.searchBooks(title, currentPage, 10).subscribe({
      next: (response) => {
        this.books = response.books;
        this.totalPages = response.totalPages;
      },
      error: (error) => console.error('There was an error!', error),
    });
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.searchBooks('victory', this.currentPage);
  }
}
