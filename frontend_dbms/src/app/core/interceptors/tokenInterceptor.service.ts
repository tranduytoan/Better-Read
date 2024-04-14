import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, skip, switchMap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { TokenDTO } from '../../shared/models/tokenDTO';
import { Router } from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // console.log('intercept:');
    // console.log(this.authService.isAuthenticated());

    // Add access token to the Authorization header if it exists
    // request = this.addToken(request);  //no more needed because we are using jwtModule config

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {  // Unauthorized (don't have access token or access token is expired)
          // if refresh token is expired, logout
          if (!this.authService.isAuthenticated) {
            this.authService.logout();
            this.router.navigate(['/login']);
            return throwError(() => new HttpErrorResponse({status: 401}));
          }
          // if refresh token is not expired, get new access token
          return this.authService.accessToken().pipe(
            switchMap((tokenDto: TokenDTO) => {
              if (!tokenDto.accessToken) {
                // setTimeout(() => console.log('#intercept can get new access token'), 1000);
                return throwError(() => error);
              }
              this.authService.saveToken(tokenDto);
              // setTimeout(() => console.log('#intercept have new access token'), 1000);
              request = this.addToken(request);   //because jwtModule dont add token to request below
              return next.handle(request);
            }),
            catchError((error: any) => {
              this.authService.logout();
              return throwError(() => error);
            })
          );
        }
        return throwError(() => error);
      })
    ) as Observable<HttpEvent<any>>;
  }

  // add access token to the Authorization header
  private addToken(request: HttpRequest<any>): HttpRequest<any> {
    const accessToken = this.authService.getAccessToken();
    if (accessToken) {
      return request.clone({
        setHeaders: {
          Authorization: `Bearer ${accessToken}`
        }
      });
    }
    return request;
  }
}
