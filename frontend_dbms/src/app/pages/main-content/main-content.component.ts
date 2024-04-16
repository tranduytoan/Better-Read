import { Component, OnInit } from '@angular/core';
import { SearchService } from "../../core/services/search.service";
import { ScrollService } from "../../core/services/scroll.service";
import { ActivatedRoute, Router } from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.css']
})
export class MainContentComponent implements OnInit {
  searchQuery: string = "";
  currentPage: number = 0;
  totalPages: number = 0;
  maxVisiblePages: number = 5;
  pageSize: number = 9;
  searchResults: any[] = [];
  selectedPrice: string = '';
  selectedCategory: string = '';
  selectedReview: string = '';
  priceRanges = [
    { value: '0-10', label: '0$ - 10$' },
    { value: '10-20', label: '10$ - 20$' },
    { value: '20-50', label: '20$ - 50$' },
    { value: '50-100', label: '50$ - 100$' },
    { value: '100-', label: '100$+' }
  ];
  constructor(
    private searchService: SearchService,
    private scrollService: ScrollService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.searchQuery = params['query'];
      this.searchBooks();
    });
  }

  getSelectedFilters(): string {
    return this.selectedPrice;
  }

  searchBooks() {
    console.log("Search ",this.searchQuery );
    if (this.searchQuery.trim() !== '') {
      // this.resetFilters();
      this.searchService.searchBooks(this.searchQuery, this.currentPage, this.pageSize, this.getSelectedFilters()).subscribe(
        (response) => {
          this.searchResults = response.content;
          this.totalPages = response.totalPages;
        },
        (error) => {
          console.error(error);
          this.toastr.error('An error occured while searching for books');
        }
      );
    }
  }


  onPageChange(page: number) {
    this.currentPage = page;
    this.searchBooks();
    this.scrollService.scrollToTop();
  }

  goToBookDetail(bookId: number) {
    this.router.navigate(['book', bookId]);
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = '/assets/images/book-cover-default.jpg';
  }

  onFilterChange() {
    this.currentPage = 0;
    this.searchBooks();
  }

  resetFilters() {
    this.selectedPrice = '';
    this.searchBooks();
  }

  filterByPrice(price: number): boolean {
    if (!this.selectedPrice) {
      return true;
    }
    const [min, max] = this.selectedPrice.split('-').map(Number);
    return price >= min && (max === 0 || !max || price <= max);
  }
}
