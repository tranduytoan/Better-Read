import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
// export class BookService {
//   private baseUrl = 'http://localhost:8080/books';
//
//   constructor(private http: HttpClient) { }
//
//   // searchBooks(title: string, page: number, size: number): Observable<{books: Book[], totalPages: number}> {
//   //   const searchUrl = `${this.baseUrl}?title=${title}&page=${page}&size=${size}`
//   //   return this.http.get<{books : Book[], totalPages: number}>(searchUrl);
//   // }
//
//   // searchBooks(title: string, page: number, size: number): Observable<BookListResponse> {
//   //   const searchUrl = `${this.baseUrl}?title=${encodeURIComponent(title)}&page=${page}&size=${size}`;
//   //   return this.http.get<BookListResponse>(searchUrl);
//   // }
//
//   searchBooks(title: string, page: number, size: number): Observable<any> {
//     const params = new HttpParams()
//       .set('title', title)
//       .set('page', page.toString())
//       .set('size', size.toString());
//
//     return this.http.get(this.baseUrl, {params});
//   }
//   getBookDetail(bookId: string): Observable<BookDetailDTO> {
//     const detailUrl = `${this.baseUrl}/${bookId}`;
//     return this.http.get<BookDetailDTO>(detailUrl);
//   }
//
//   getRecommendedBooks(bookId: number): Observable<any> {
//     return this.http.get<Book[]>(`${this.baseUrl}/${bookId}/recommended`);
//   }
// }
//
export class BookService {
  private apiUrl = 'http://localhost:8080/api/v1/book';

  constructor(private http: HttpClient) { }

  getAllBooks(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  searchBooks(title: string, page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/search?title=${title}&page=${page}&size=${size}`);
  }
}
