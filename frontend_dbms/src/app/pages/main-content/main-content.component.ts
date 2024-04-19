import { Component, OnInit } from '@angular/core';
import { SearchService } from "../../core/services/search.service";
import { ScrollService } from "../../core/services/scroll.service";
import { ActivatedRoute, Router } from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {BookDTO} from "../../shared/models/bookDTO";

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
  pageSize: number = 12;
  searchResults: any[] = [];
  selectedPrice: string = '';
  selectedPublisher: string = '';
  selectedCategory: string = '';
  publishers: string[] = ['Nhà Xuất Bản Kim Đồng', 'NXB Trẻ'];
  categories: string[] = ['Manga', 'Kinh tế', 'Nuôi dạy con'];
  books: BookDTO[] = [];
  priceRanges = [
    { value: '0-10', label: '0$ - 10$' },
    { value: '10-20', label: '10$ - 20$' },
    { value: '20-50', label: '20$ - 50$' },
    { value: '50-100', label: '50$ - 100$' },
    { value: '100-', label: '100$+' }
  ];
  visibleCategoriesLimit = 5;
  showAllCategories = false;

  get visibleCategories(): string[] {
    return this.showAllCategories ? this.categories : this.categories.slice(0, this.visibleCategoriesLimit);
  }
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

  // searchBooks() {
  //   console.log("Search ",this.searchQuery );
  //   if (this.searchQuery.trim() !== '') {
  //     // this.resetFilters();
  //     this.searchService.searchBooks(this.searchQuery, this.currentPage, this.pageSize, this.getSelectedFilters()).subscribe(
  //       (response) => {
  //         this.searchResults = response.content;
  //         this.totalPages = response.totalPages;
  //       },
  //       (error) => {
  //         console.error(error);
  //         this.toastr.error('An error occured while searching for books');
  //       }
  //     );
  //   }
  // }

  // searchBooks() {
  //   console.log("Search ",this.searchQuery );
  //   if (this.searchQuery.trim() !== '') {
  //     // this.resetFilters();
  //     this.searchService.searchBooks(this.searchQuery, this.currentPage, this.pageSize).subscribe(
  //       (response) => {
  //         this.searchResults = response.content;
  //         this.totalPages = response.totalPages;
  //       },
  //       (error) => {
  //         console.error(error);
  //         this.toastr.error('An error occured while searching for books');
  //       }
  //     );
  //   }
  // }

  searchBooks() {
    console.log("Search ", this.searchQuery);
    if (this.searchQuery.trim() !== '') {
      const [minPrice, maxPrice] = this.selectedPrice.split('-').map(Number);
      const request = {
        title: this.searchQuery,
        category: this.selectedCategory,
        publisher: this.selectedPublisher,
        minPrice: minPrice !== 0 ? minPrice : null,
        maxPrice: maxPrice !== 0 ? maxPrice : null,
      };

      this.searchService.searchBooks(request, this.currentPage, this.pageSize).subscribe(
        (response) => {
          this.searchResults = response.content;
          this.totalPages = response.totalPages;
        },
        (error) => {
          console.error(error);
          this.toastr.error('An error occurred while searching for books');
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
    this.selectedCategory = '';
    this.selectedPublisher = '';
    this.searchBooks();
  }

  toggleShowAllCategories(): void {
    this.showAllCategories = true;
  }

}
