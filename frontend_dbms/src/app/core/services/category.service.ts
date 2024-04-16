import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "../../shared/models/category";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/v1';
  constructor(private http: HttpClient) { }
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }

  getCategoryHierarchy(categoryId: number): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories/${categoryId}/hierarchy`);
  }
}
