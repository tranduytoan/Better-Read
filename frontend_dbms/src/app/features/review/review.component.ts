import {Review} from "../../shared/models/review";
import {Component, Input, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ReviewService} from "../../core/services/review.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {
  @Input() bookId!: string;
  @Input() userId!: number | null;
  reviews: Review[] = [];
  showReviewForm: boolean = false;
  reviewForm!: FormGroup;
  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 0;
  rating: number = 0;
  hoverRating: number = 0;
  constructor(
    private formBuilder: FormBuilder,
    private reviewService: ReviewService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.reviewForm = this.formBuilder.group({
      rating: ['', Validators.required],
      comment: ['', [Validators.required, Validators.minLength(10)]]
    });

    this.getReviews();
  }

  getReviews(): void {
    if (this.bookId) {
      this.reviewService.getReviewsByBookId(this.bookId, this.currentPage, this.pageSize)
        .subscribe(response => {
          this.reviews = response.content;
          this.currentPage = response.pageNumber;
          this.totalPages = response.totalPages;
        });
    }
  }

  selectRating(value: number): void {
    this.reviewForm.get('rating')?.setValue(value);
    console.log('Selected rating:', value);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.getReviews();
  }

  onPageSizeChange(pageSize: number): void {
    this.pageSize = pageSize;
    this.currentPage = 0;
    this.getReviews();
  }

  openReviewForm(): void {
    this.showReviewForm = true;
  }

  cancelReviewForm(): void {
    this.showReviewForm = false;
    this.reviewForm.reset();
    this.rating = 0;
  }

  submitReview(): void {
    if (this.reviewForm.invalid) {
      return;
    }

    const rating = this.reviewForm.get('rating')?.value;
    const comment = this.reviewForm.get('comment')?.value;
    const title = '';

    if (this.userId) {
      this.reviewService.addReview(+this.bookId, this.userId, rating, title, comment).subscribe(
        (newReview: Review) => {
          this.reviews.unshift(newReview);
          this.showReviewForm = false;
          this.reviewForm.reset();
          this.toastr.success('Review submitted successfully!');
          },
        (error) => {
          console.error('Error submitting review:', error);
          this.toastr.error('Please try again.');
        }
      );
    }
  }

  toggleReviewExpand(review: Review): void {
    // review.expanded = !review.expanded;
  }

  protected readonly Math = Math;

  onStarHover(rating: number): void {
    this.hoverRating = rating;
  }

  onStarLeave(): void {
    this.hoverRating = 0;
  }
}
