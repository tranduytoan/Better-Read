// import {Component, OnInit} from '@angular/core';
// import {Userbooklist} from "../../models/user-book-list";
// import {ActivatedRoute} from "@angular/router";
// import {UserBooksService} from "../../services/user-books.service";
// import {UserBook} from "../../models/user-book";
// import {AuthService} from "../../services/auth.service";
// import {UserService} from "../../services/user.service";
// import {Book} from "../../models/book";
// import {BookService} from "../../services/book.service";
// import {BookDetailDTO} from "../../models/book-detail-dto";
//
// @Component({
//   selector: 'app-user-books',
//   templateUrl: './user-books.component.html',
//   styleUrl: './user-books.component.css'
// })
// export class UserBooksComponent implements OnInit{
//   userBooks: UserBook[] = [];
//   userId: number | null | undefined;
//   constructor(
//     private userBookService: UserBooksService,
//     private route: ActivatedRoute,
//     private authService: AuthService,
//     private userService: UserService,
//     public bookService: BookService
//   ) { }
//   ngOnInit(): void {
//     this.userService.userId$.subscribe(userId => {
//       this.userId = userId;
//     });
//     this.loadUserBooks();
//   }
//
//   markAsRead(bookId: string) {
//     if (this.userId) {
//       this.userBookService.markAsRead(bookId, this.userId).subscribe({
//         next: () => {
//           this.loadUserBooks();
//         },
//         error: (error) => console.error('There was an error!', error)
//       });
//     } else {
//       console.log('User is not logged in');
//     }
//   }
//
//   removeBook(bookId: string) {
//     if (this.userId) {
//       this.userBookService.removeBook(bookId, this.userId).subscribe({
//         next: () => {
//           this.loadUserBooks();
//         },
//         error: (error) => console.error('There was an error!', error)
//       });
//     } else {
//       console.log('User is not logged in');
//     }
//   }
//
//   loadUserBooks(): void {
//     if (this.userId) {
//       this.userBookService.getUserBooks(this.userId).subscribe(userBooks => {
//         this.userBooks = userBooks;
//         this.fetchBookDetails();
//         console.log('userBooks:', this.userBooks, this.userId);
//       });
//     } else {
//       console.log('User is not logged in');
//     }
//   }
//   updateBookStatus(book: UserBook) {
//     console.log('Book status updated: ', book);
//   }
//
//   rateBook(book: UserBook, star: number) {
//   }
//
//   fetchBookDetails() {
//     this.userBooks.forEach((book) => {
//       this.bookService.getBookDetail(book.bookId).subscribe(
//         (bookDetail: BookDetailDTO) => {
//           book.title = bookDetail.title;
//           book.cover = bookDetail.covers?.[0] || '';
//         },
//         (error) => {
//           console.log('error retrieving book', error);
//         }
//       )
//     })
//   }
// }
