import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, throwError} from "rxjs";
import {Cart} from "../models/cart";
import {Book} from "../models/book";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080/api/v1/carts';
  private cartSubject: BehaviorSubject<Cart> = new BehaviorSubject<Cart>({} as Cart);
  cart$: Observable<Cart> = this.cartSubject.asObservable();
  private userId: number = 1;

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.loadCart();
  }

  private loadCart() {
    this.http.get<Cart>(`${this.apiUrl}?userId=${this.userId}`).subscribe(
      (cart: Cart) => {
        this.cartSubject.next(cart);
      },
      (error) => {
        console.log('Error loading cart:', error);
      }
    );
  }

  addToCart(bookId: number, userId: number, quantity: number = 1) {
    const url = `${this.apiUrl}/items`;
    const params = new HttpParams()
      .set('bookId', bookId.toString())
      .set('userId', userId.toString())
      .set('quantity', quantity.toString());

    return this.http.post<Cart>(url, { bookId, userId, quantity }, { params });
  }

  updateQuantity(bookId: number, quantity: number) {
    const url = `${this.apiUrl}/items/${bookId}`;
    const params = {
      userId: this.userId,
      quantity: quantity
    };
    this.http.put<Cart>(url, null, { params }).subscribe(
      (cart: Cart) => {
        this.cartSubject.next(cart);
      },
      (error) => {
        console.log('Error updating quantity:', error);
      }
    );
  }

  removeFromCart(bookId: number) {
    const url = `${this.apiUrl}/items/${bookId}`;
    const params = {
      userId: this.userId
    };
    this.http.delete<Cart>(url, { params }).subscribe(
      (cart: Cart) => {
        this.cartSubject.next(cart);
      },
      (error) => {
        console.log('Error removing from cart:', error);
      }
    );
  }

  clearCart() {
    const url = `${this.apiUrl}`;
    const params = {
      userId: this.userId
    };
    this.http.delete<Cart>(url, { params }).subscribe(
      (cart: Cart) => {
        this.cartSubject.next(cart);
      },
      (error) => {
        console.log('Error clearing cart:', error);
      }
    );
  }
}
