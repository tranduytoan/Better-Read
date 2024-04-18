import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/v1/products';
  constructor(private http: HttpClient) { }

  getSuggestions(query: string): Observable<string[]> {
    const url = `${this.apiUrl}/autocomplete?query=${query}`;
    return this.http.get<string[]>(url);
}
}
