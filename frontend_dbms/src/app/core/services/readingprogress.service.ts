import { Injectable } from '@angular/core';
import {ReadingProgress} from "../../shared/models/readingprogress";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ReadingProgressService {
  private apiUrl = 'http://localhost:8080/api/v1/reading-progress';

  constructor(private http: HttpClient) { }

  getReadingProgressByUserId(userId: number, page: number, size: number): Observable<ReadingProgress[]> {
    const params = { userId, page, size };
    return this.http.get<{content: ReadingProgress[]}>(`${this.apiUrl}`, { params }).pipe(
      map(response => response.content)
    );
  }

  updateReadingProgress(bookId: number, request: Partial<ReadingProgress>): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${bookId}`, request);
  }

  addBookToReadingProgress(request: Partial<ReadingProgress>): Observable<ReadingProgress> {
    return this.http.post<ReadingProgress>(`${this.apiUrl}`, request);
  }

  getInProgressBooksByUserId(userId: number): Observable<ReadingProgress[]> {
    const params = { userId };
    return this.http.get<ReadingProgress[]>(`${this.apiUrl}/in-progress`, { params });
  }

  getCompletedBooksByUserId(userId: number): Observable<ReadingProgress[]> {
    const params = { userId };
    return this.http.get<ReadingProgress[]>(`${this.apiUrl}/completed`, { params });
  }
}
