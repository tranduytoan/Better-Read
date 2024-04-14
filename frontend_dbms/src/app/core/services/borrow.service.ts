// import { Injectable } from '@angular/core';
// import {HttpClient} from "@angular/common/http";
// import {Observable} from "rxjs";
//
// @Injectable({
//   providedIn: 'root'
// })
// export class BorrowService {
//   public apiUrl = 'http://localhost:8080/api/v1/borrow';
//
//   constructor(private http: HttpClient) { }
//
//   borrowBook(bookId: string, userId: number): Observable<any> {
//       const url = `${this.apiUrl}/${bookId}/user/${userId}`;
//       return this.http.post(url, null);
//   }
//
//   getUserBorrowings(userId: number): Observable<any[]> {
//     const url = `${this.apiUrl}/user/${userId.toString()}`;
//     return this.http.get<any[]>(url);
//   }
// }
