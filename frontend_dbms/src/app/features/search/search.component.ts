import {Component, ElementRef, EventEmitter, Output, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {ProductService} from "../../core/services/product.service";
import {debounceTime, distinctUntilChanged, fromEvent} from "rxjs";
import {map, switchMap} from "rxjs/operators";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchQuery: string = "";
  suggestions: string[] = [];

  @ViewChild('searchInput', { static: true }) searchInput!: ElementRef;

  constructor(private router: Router,
              private productService: ProductService) {
  }

  ngOnInit() {
    this.setupAutocomplete();
  }

  setupAutocomplete() {
    fromEvent(this.searchInput.nativeElement, 'input')
      .pipe(
        map((event: any) => event.target.value),
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((query: string) => this.productService.getSuggestions(query))
      )
      .subscribe((suggestions: string[]) => {
        this.suggestions = suggestions;
      });
  }
  search() {
    this.router.navigate(['/main-content'], { queryParams: { query: this.searchQuery } });
  }

  selectSuggestion(suggestion: string) {
    this.searchQuery = suggestion;
    this.suggestions = [];
    this.search();
  }
}
