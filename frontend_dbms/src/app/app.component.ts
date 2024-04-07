import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'BetterRead';
  searchResults: any[] = [];
  isLandingPage: boolean = true;

  constructor(private router: Router) {}

  onSearchResults(results: any[]) {
    this.searchResults = results;
  }

  onActivate(event: any) {
    this.isLandingPage = event.constructor.name === 'LandingPageComponent';
  }
  isAuthPage(): boolean {
    const currentUrl = this.router.url;
    return currentUrl === '/login' || currentUrl === '/signup' || currentUrl === '/register';
  }
}
