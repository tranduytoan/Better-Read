import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {OnInit} from "@angular/core";
import {AuthService} from "./core/services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'BetterRead';
  searchResults: any[] = [];
  isLandingPage: boolean = true;

  constructor(private router: Router, private authService: AuthService) {}

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

  ngOnInit(): void {
    console.log('Foreveread App started!');
    this.authService.isAuthenticated();
  }
}
