import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {UserService} from "../services/user.service";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
  @Output() searchResults: EventEmitter<any[]> = new EventEmitter<any[]>();
  showMenu = false;
  userId: number | null | undefined;
  isLoggedIn = false;
  showDropdown = false;
  avatarUrl = 'assets/images/book-cover-default.jpg';

  constructor(private userService: UserService, private authService: AuthService,
              private searchService: SearchService) {
  }

  ngOnInit() {
    this.userService.userId$.subscribe(userId => {
      this.userId = userId;
    });

    this.authService.isLoggedIn$.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    })
  }

  toggleMenu() {
    this.showMenu = !this.showMenu;
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: (response) => {
        // console.log('logout sucessful:', response);
        this.authService.clearToken();
        this.showDropdown = false;
        window.location.reload()
      },
      error: (error) => {
        console.error('logout error:', error);
      }
    });
  }

  logoutAll(): void {
    this.authService.logoutAll().subscribe({
      next: (response) => {
        // console.log('logoutAll sucessful:', response);
        this.authService.clearToken();
        this.showDropdown = false;
        window.location.reload()
      },
      error: (error) => {
        console.error('logoutAll error:', error);
      }
    });
  }

  onSearchTriggered(searchQuery: string) {
    this.searchService.searchBooks(searchQuery, 0, 10).subscribe(
      (response) => {
        this.searchResults.emit(response.content);
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
