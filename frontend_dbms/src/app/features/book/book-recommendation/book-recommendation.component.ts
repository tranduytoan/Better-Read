import {Component, Input} from '@angular/core';
import {BookService} from "../../../core/services/book.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-book-recommendation',
  templateUrl: './book-recommendation.component.html',
  styleUrl: './book-recommendation.component.css'
})
export class BookRecommendationComponent {
  @Input() bookId: number = 0;
  recommendedBooks: any[] = [];

  constructor(private bookService: BookService,
              private router: Router) { }

  ngOnInit(): void {
    this.getRecommendations();
  }

  getRecommendations(): void {
    this.bookService.getRecommendedBooks(this.bookId)
      .subscribe(books => {
        this.recommendedBooks = books;
        console.log(this.recommendedBooks);
      });
  }

  currentIndex = 0;
  itemWidth = 176;
  visibleItems = 4;

  get currentPosition() {
    return -this.currentIndex * this.itemWidth;
  }

  previousSlide() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
    }
  }

  nextSlide() {
    if (this.currentIndex < this.recommendedBooks.length - this.visibleItems) {
      this.currentIndex++;
    }
  }

  goToBookDetail(bookId: number) {
    this.router.navigate(['/book', bookId]);
  }
}
