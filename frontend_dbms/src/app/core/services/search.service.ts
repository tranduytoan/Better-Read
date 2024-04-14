import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {BookDTO} from "../../shared/models/bookDTO";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = 'http://localhost:8080/api/v1/book';
  constructor(private http: HttpClient) { }

  getBookDetails(bookId: string): Observable<BookDTO> {
    const url = `${this.apiUrl}/${bookId}`;
    return this.http.get<BookDTO>(url);
  }

  getAllBooks(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }


  searchBooks(searchQuery: string, page: number, pageSize: number, price: string): Observable<any> {
    const params = new HttpParams()
      .set('query', searchQuery)
      .set('page', page.toString())
      .set('size', pageSize.toString())
      .set('price', price)
    return this.http.get<any>(`${this.apiUrl}/search`, { params });
  }


}
