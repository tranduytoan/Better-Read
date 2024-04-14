import {Component, EventEmitter, Output} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchQuery: string = "";
  constructor(private router: Router) {
  }
  search() {
    this.router.navigate(['/main-content'], { queryParams: { query: this.searchQuery } });
  }
}
