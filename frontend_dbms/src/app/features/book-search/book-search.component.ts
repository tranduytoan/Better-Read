// import {Component, OnInit} from '@angular/core';
// import {Book} from "../../models/book";
// import {BookService} from "../../services/book.service";
// import {ScrollService} from "../../services/scroll.service";
// import {UserService} from "../../services/user.service";
//
// @Component({
//   selector: 'app-book-search',
//   templateUrl: './book-search.component.html',
//   styleUrl: './book-search.component.css'
// })
//
// export class BookSearchComponent  implements OnInit{
//
//   books: Book[] = [];
//   totalPages: number = 20;
//   maxVisiblePages: number = 5;
//   currentPage: number = 1;
//   searchQuery: string = '';
//   pageSize = 12;
//   userId: string | null | undefined;
//
//   constructor(private bookService: BookService,
//               private scrollService: ScrollService) { }
//   ngOnInit(): void {
//     this.searchBooks(this.searchQuery, this.currentPage);
//   }
//
//
//   searchBooks(title: string, currentPage: number) {
//     this.bookService.searchBooks(title, currentPage, this.pageSize).subscribe({
//       next: (response) => {
//         this.books = response.books;
//         this.totalPages = response.totalPages;
//       },
//       error: (error) => console.error('There was an error when searching books!', error),
//     });
//   }
//
//   goToPage(page: number) {
//     this.currentPage = page;
//     this.searchBooks(this.searchQuery, this.currentPage);
//     this.scrollService.scrollToTop();
//   }
// }
