import { Component, OnInit } from '@angular/core';
import { SearchService } from "../services/search.service";
import { ScrollService } from "../services/scroll.service";
import { ActivatedRoute, Router } from "@angular/router";

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
  pageSize: number = 10;
  searchResults: any[] = [];

  constructor(
    private searchService: SearchService,
    private scrollService: ScrollService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.searchQuery = params['query'];
      this.searchBooks(this.searchQuery, this.currentPage, this.pageSize);
    });
  }

  searchBooks(searchQuery: string, page: number, pageSize: number) {
    console.log("Search ",searchQuery );
    if (searchQuery.trim() !== '') {
      this.searchService.searchBooks(searchQuery, page, pageSize).subscribe(
        (response) => {
          this.searchResults = response.content;
          this.totalPages = response.totalPages;
          this.currentPage = page;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }


  onPageChange(page: number) {
    this.searchBooks(this.searchQuery, page, this.pageSize);
    this.scrollService.scrollToTop();
  }

  goToBookDetail(bookId: number) {
    this.router.navigate(['book', bookId]);
  }
}
