import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, Observable, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null);
  public userId$: Observable<number | null> = this.userIdSubject.asObservable();

  private apiUrl = 'http://localhost:8080/api/v1/user';
  constructor(private http: HttpClient) {
  }

  registerUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }
  setUserId(userId: number) {
    this.userIdSubject.next(userId);
  }

  clearUserId() {
    this.userIdSubject.next(null);
  }
}
