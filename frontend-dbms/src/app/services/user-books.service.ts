import {Injectable, OnInit} from '@angular/core';
import {Userbooks} from "../models/userbooks";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserBooksService{
  private apiUrl = 'http://localhost:8080/userbooks';

  constructor(private http: HttpClient) { }

  addUserBook(userBook: Userbooks) {
    return this.http.post<Userbooks>(this.apiUrl, userBook);
  }

  markAsRead(bookId: string, userId: string): Observable<Userbooks> {
    return this.http.put<Userbooks>(`${this.apiUrl}/${bookId}/mark-as-read`, {userId});
  }

  removeBook(bookId: string, userId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${bookId}/remove`, {params: {userId}});
  }

  getBooksToRead(userId: string): Observable<Userbooks[]> {
    return this.http.get<Userbooks[]>(`${this.apiUrl}/want-to-read/${userId}`);
  }

  getReadBooks(userId: string): Observable<Userbooks[]> {
    return this.http.get<Userbooks[]>(`${this.apiUrl}/read/${userId}`);
  }

  getUserBooks(userId: string): Observable<Userbooks[]> {
    return this.http.get<Userbooks[]>(`${this.apiUrl}/${userId}`);
  }

  getBookById(bookId: string): Observable<Userbooks> {
    return this.http.get<Userbooks>(`${this.apiUrl}/${bookId}`);
  }

}
