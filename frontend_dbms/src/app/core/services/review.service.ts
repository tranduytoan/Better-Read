import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface ReviewResponse {
  content: any[];
  totalElements: number;
  totalPages: number;
  pageNumber: number;
  pageSize: number;
}

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl = 'http://localhost:8080/api/v1/reviews';

  constructor(private http: HttpClient) { }

  getReviewsByBookId(bookId: string, page: number = 0, size: number = 10): Observable<ReviewResponse> {
    return this.http.get<ReviewResponse>(`${this.baseUrl}/${bookId}?page=${page}&size=${size}`);
  }

  addReview(bookId: number, userId: number, rating: number, title: string, comment: string): Observable<any> {
    const body = { userId, rating, title, comment };
    return this.http.post(`${this.baseUrl}/${bookId}`, body, { observe: 'response' });
  }

  deleteReview(reviewId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${reviewId}`);
  }
}
