import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BookDTO} from "../../../shared/models/bookDTO";
import {SearchService} from "../../../core/services/search.service";
import {CartService} from "../../../core/services/cart.service";
import {UserService} from "../../../core/services/user.service";
import {ToastrService} from "ngx-toastr";
import {ReadingProgressService} from "../../../core/services/readingprogress.service";
import {ReadingProgress} from "../../../shared/models/readingprogress";
import {ReviewService} from "../../../core/services/review.service";
import {Category} from "../../../shared/models/category";
import {CategoryService} from "../../../core/services/category.service";
import {ScrollService} from "../../../core/services/scroll.service";
@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css'],
})
export class BookDetailComponent implements OnInit {
  book!: BookDTO;
  bookId!: string | null;
  quantity: number = 1;
  userId!: number | null;
  expandedDescription: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private searchService: SearchService,
    private cartService: CartService,
    private userService: UserService,
    private toastr: ToastrService,
    private readingprogressService: ReadingProgressService,
    private reviewService: ReviewService,
    private categoryService: CategoryService,
    private scrollService: ScrollService
  ) {}

  ngOnInit(): void {
    // this.bookId = this.route.snapshot.paramMap.get('id');
    this.route.paramMap.subscribe(params => {
      this.bookId = params.get('id');
      if (this.bookId) {
        this.searchService.getBookDetails(this.bookId).subscribe(
          (book: BookDTO) => {
            this.book = book;
            if (this.book.category && this.book.category.length > 0) {
              this.loadCategoryHierarchy(this.book.category[0].id);
            }
            this.scrollService.scrollToTop();

          },
          (error) => {
            console.error('Error fetching book details:', error);
            this.toastr.error('Failed to load book details.');
            this.router.navigate(['/404']);
          }
        );
      }
    })


    this.userService.userId$.subscribe(userId => {
      this.userId = userId;
    })
  }


  increaseQuantity() {
    if (this.book && this.book.inventory && this.quantity < this.book.inventory.quantity) {
      this.quantity++;
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

  addToReadingProgress() {
    if (this.userId !== null && this.book && this.book.id) {
      const request: Partial<ReadingProgress> = {
        userId: this.userId,
        bookId: this.book.id,
        progress: 'NOT_STARTED'
      };

      this.readingprogressService.addBookToReadingProgress(request).subscribe(
        () => {
          this.toastr.success('Book added to reading progress.');
        },
        (error: any) => {
          console.error('Error adding book to reading progress:', error);
          this.toastr.error('Failed to add book to reading progress.');
        }
      );
    } else {
      this.toastr.error('User ID is null or book details are missing. Cannot add book to reading progress.');
    }
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = '/assets/images/book-cover-default.jpg';
  }

  toggleDescription() {
    this.expandedDescription = !this.expandedDescription;
  }

  protected readonly Math = Math;

  categoryHierarchy: Category[] | undefined;

  loadCategoryHierarchy(categoryId: number) {
    this.categoryService.getCategoryHierarchy(categoryId).subscribe({
      next: (categories) => {
        this.categoryHierarchy = categories;
      },
      error: (error) => {
        console.error('Failed to load category hierarchy', error);
      }
    });
  }
}
