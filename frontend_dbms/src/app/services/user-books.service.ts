// import {Injectable, OnInit} from '@angular/core';
// import {UserBook} from "../models/user-book";
// import {HttpClient, HttpParams} from "@angular/common/http";
// import {Observable} from "rxjs";
// import {Userbooklist} from "../models/user-book-list";
// import {BookDetailDTO} from "../models/book-detail-dto";
//
// @Injectable({
//   providedIn: 'root'
// })
// export class UserBooksService{
//   private apiUrl = 'http://localhost:8080/api/v1/userbooks';
//
//   constructor(private http: HttpClient) { }
//
//   addToReadingList(bookId: string | null, userId: number | null): Observable<any> {
//     const url = `${this.apiUrl}/${userId}/reading-list`;
//     return this.http.post(url, {bookId});
//   }
//
//
//   markAsRead(bookId: string, userId: number): Observable<UserBook> {
//     return this.http.put<UserBook>(`${this.apiUrl}/${bookId}/mark-as-read`, {userId});
//   }
//
//   removeBook(bookId: string, userId: number): Observable<void> {
//     return this.http.delete<void>(`${this.apiUrl}/${bookId}/remove`, {params: {userId}});
//   }
//
//   getBooksToRead(userId: number): Observable<UserBook[]> {
//     return this.http.get<UserBook[]>(`${this.apiUrl}/want-to-read/${userId}`);
//   }
//
//   getReadBooks(userId: number): Observable<UserBook[]> {
//     return this.http.get<UserBook[]>(`${this.apiUrl}/read/${userId}`);
//   }
//
//   getUserBooks(userId: number | null): Observable<UserBook[]> {
//     return this.http.get<UserBook[]>(`${this.apiUrl}/${userId}`);
//   }
//
//   getBookById(bookId: string): Observable<UserBook> {
//     return this.http.get<UserBook>(`${this.apiUrl}/${bookId}`);
//   }
//
//
// }
