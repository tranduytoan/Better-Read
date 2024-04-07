// import {Component, OnInit} from '@angular/core';
// import {Book} from "../../models/book";
// import {BookService} from "../../services/book.service";
// import {ActivatedRoute} from "@angular/router";
// import {BookDetailDTO} from "../../models/book-detail-dto";
// import {BorrowService} from "../../services/borrow.service";
// import {AuthService} from "../../services/auth.service";
// import {UserService} from "../../services/user.service";
// import {UserBooksService} from "../../services/user-books.service";
//
// @Component({
//   selector: 'app-book-detail',
//   templateUrl: './book-detail.component.html',
//   styleUrls: ['./book-detail.component.css']
// })
// export class BookDetailComponent implements OnInit{
//   // book: BookDetailDTO | null = null;
//   recommendedBooks: Book[] = [];
//   book: any;
//   bookId: string | null | undefined;
//   userId: number | null | undefined;
//   constructor(
//     private route: ActivatedRoute,
//     private bookService: BookService,
//     public borrowService: BorrowService,
//     private userService: UserService,
//     private userBookService: UserBooksService
//   ) { }
//   ngOnInit(): void {
//     const bookId = this.route.snapshot.paramMap.get('id');
//     this.getBookDetail();
//
//     this.userService.userId$.subscribe(userId => {
//       this.userId = userId;
//     });
//     // console.log('BookDetailComponent initialized');
//   }
//
//   getBookDetail() {
//     this.bookId = this.route.snapshot.paramMap.get('id');
//     if (this.bookId) {
//       this.bookService.getBookDetail(this.bookId).subscribe({
//         next: (book) => {
//           console.log('Book data:', book);
//           this.book = book;
//         },
//         error: (error) => console.error(']Error fetching book detail', error),
//       });
//     }
//   }
//
//   borrowBook(): void {
//     if (this.book && this.book.id && this.userId) {
//       this.borrowService.borrowBook(this.book.id, this.userId).subscribe(
//         () => console.log('Book borrowed successfully'),
//         error => console.error('Error borrowing book', error)
//       );
//     } else {
//       console.error('Cannot book-list book: Book details or User ID is missing.');
//     }
//   }
//
//   getRecommendedBooks(bookId: number) {
//     this.bookService.getRecommendedBooks(bookId).subscribe(
//       (books) => {
//         this.recommendedBooks = books;
//       },
//       (error) => {
//         console.error('Error fetching recommended books:', error);
//       }
//     );
//   }
//   //
//   addToBookself() {
//     if (this.bookId && this.userId) {
//       this.userBookService.addToReadingList(this.bookId, this.userId).subscribe(
//         () => console.log("Book added to reading list successfully"),
//         error => console.error("Error adding book to reading list", error)
//       );
//     } else {
//       console.error("Book Id or userid is missing");
//     }
//   }
// }
//
//

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BookDTO} from "../../models/bookDTO";
import {SearchService} from "../../services/search.service";
import {CartService} from "../../services/cart.service";
import {UserService} from "../../services/user.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {
  book!: BookDTO;
  quantity: number = 1;
  userId!: number | null;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private searchService: SearchService,
    private cartService: CartService,
    private userService: UserService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    const bookId = this.route.snapshot.paramMap.get('id');
    if (bookId) {
      this.searchService.getBookDetails(bookId).subscribe(
        (book: BookDTO) => {
          this.book = book;
        },
        (error) => {
          console.error('Error fetching book details:', error);
          this.toastr.error('Failed to load book details.');
          this.router.navigate(['/404']);
        }
      );
    }

    this.userService.userId$.subscribe(userId => {
      this.userId = userId;
    })
  }

  increaseQuantity() {
    if (this.quantity < this.book.inventory.quantity) {
      this.quantity++;
    } else {
      console.log('Maximum quantity reached');
    }
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    if (this.userId !== null && this.book && this.book.id) {
      this.cartService.addToCart(this.book.id, this.userId, this.quantity).subscribe({
        next: () => {
          this.toastr.success('Book added to cart.');
          // this.router.navigate(['/home']);
        },
        error: (error: any) => {
          console.error('Error adding book to cart:', error);
          this.toastr.error('Failed to add book to cart.');
        }
      });
    } else {
      this.toastr.error('User ID is null or book details are missing. Cannot add book to cart.');
    }
  }
  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = '/assets/images/book-cover-default.jpg';
  }
}
