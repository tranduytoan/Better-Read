import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { map, tap } from 'rxjs/operators';
import {BehaviorSubject, Observable} from "rxjs";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.isLoggedInSubject.asObservable();
  constructor(private http: HttpClient, private jwtHelper: JwtHelperService,
              private userService: UserService) {}

  login(username: string, password: string) {
    return this.http.post<{token: string, userId: string}>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(response => {
        localStorage.setItem('access_token', response.token);
        this.isLoggedInSubject.next(true);
        this.userService.setUserId(+response.userId);
      })
    );
  }

  signup(username: string, email: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/register`, { username, email, password });
  }

  logout() {
    localStorage.removeItem('access_token');
    this.isLoggedInSubject.next(false);
    this.userService.clearUserId();
  }

  isAuthenticated() {
    const token = localStorage.getItem('access_token');
    return !this.jwtHelper.isTokenExpired(token);
  }
}
