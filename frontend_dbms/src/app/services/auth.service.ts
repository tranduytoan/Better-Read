import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { map, tap } from 'rxjs/operators';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserService } from './user.service';
import { TokenDTO } from '../models/tokenDTO';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.isLoggedInSubject.asObservable();
  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService,
    private userService: UserService
  ) {}

  login(username: string, password: string) {
    return this.http
      .post<{ userId: string; accessToken: string; refreshToken: string }>(
        `${this.apiUrl}/login`,
        { username, password }
      )
      .pipe(
        tap((response) => {
          this.saveToken(response as TokenDTO);
          // console.log('login response:', response);
          // console.log('userId:', this.getUserId());
          // console.log('accessToken:', this.getAccessToken());
          // console.log('refreshToken:', this.getRefreshToken());
          this.isLoggedInSubject.next(true);
          this.userService.setUserId(+response.userId);
          // console.log('isLoggedIn:', this.isLoggedInSubject.value);
        })
      );
  }

  signup(username: string, email: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/register`, {
      username,
      email,
      password,
    });
  }

  logout(): Observable<TokenDTO> {
    // console.log('logout');
    this.isLoggedInSubject.next(false);
    this.userService.clearUserId();
    let refreshToken = this.getRefreshToken();
    return this.http.post<TokenDTO>(`${this.apiUrl}/logout`, {refreshToken});
  }

  logoutAll(): Observable<TokenDTO> {
    // console.log('logoutAll');
    this.isLoggedInSubject.next(false);
    this.userService.clearUserId();
    let refreshToken = this.getRefreshToken();
    return this.http.post<TokenDTO>(`${this.apiUrl}/logout-all`, {refreshToken});
  }

  // get new access token
  accessToken(): Observable<TokenDTO> {
    let tokenDto: TokenDTO = {
      userId: this.getUserId(),
      accessToken: this.getAccessToken(),
      refreshToken: this.getRefreshToken(),
    };
    return this.http.post<TokenDTO>(`${this.apiUrl}/access-token`, tokenDto);
  }

  // get new refresh token and access token
  refreshToken(): Observable<TokenDTO> {
    let tokenDto: TokenDTO = {
      userId: this.getUserId(),
      accessToken: this.getAccessToken(),
      refreshToken: this.getRefreshToken(),
    };
    return this.http.post<TokenDTO>(`${this.apiUrl}/refresh-token`, tokenDto);
  }

  isAuthenticated() {
    const token = localStorage.getItem('refresh_token');
    try {
      if (this.jwtHelper.isTokenExpired(token)) {
        this.isLoggedInSubject.next(false);
        return false;
      } else {
        this.isLoggedInSubject.next(true);
        return true;
      }
    } catch (error) { //fix token in local storage is not jwt token
      this.isLoggedInSubject.next(false);
      this.userService.clearUserId();
      this.clearToken();
      return false;
    }
  }


// Some helper methods

  saveToken(tokenDto: TokenDTO) {
    localStorage.setItem('user_id', tokenDto.userId);
    localStorage.setItem('access_token', tokenDto.accessToken);
    localStorage.setItem('refresh_token', tokenDto.refreshToken);
  }

  clearToken() {
    localStorage.removeItem('user_id');
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    // console.log('Token cleared');
  }

  getUserId(): string {
    return localStorage.getItem('user_id') as string;

  }

  getAccessToken(): string {
    return localStorage.getItem('access_token') as string;
  }

  getRefreshToken(): string {
    return localStorage.getItem('refresh_token') as string;
  }
}
