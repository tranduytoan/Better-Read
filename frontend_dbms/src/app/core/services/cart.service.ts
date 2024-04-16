import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {BehaviorSubject, catchError, filter, Observable, take, throwError} from "rxjs";
import {Cart} from "../../shared/models/cart";
import {Book} from "../../shared/models/book";
import {ToastrService} from "ngx-toastr";
import {UserService} from "./user.service";
import {Order} from "../../shared/models/order";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080/api/v1/carts';
  private cartSubject: BehaviorSubject<Cart> = new BehaviorSubject<Cart>({} as Cart);
  cart$: Observable<Cart> = this.cartSubject.asObservable();
  userId!: number | null;
  constructor(private http: HttpClient, private toastr: ToastrService, private userService: UserService) {
    this.loadCart();
    this.userService.userId$.subscribe(userId => {
      this.userId = userId;
    })
  }

  loadCart() {
    this.userService.userId$.pipe(
      filter(userId => userId !== null),
      take(1)
    ).subscribe(userId => {
      this.http.get<Cart>(`${this.apiUrl}?userId=${userId}`).subscribe(
        (cart: Cart) => {
          this.cartSubject.next(cart);
        },
        (error) => {
          console.log('Error loading cart:', error);
        }
      );
    });
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
    if (this.userId) {
      const url = `${this.apiUrl}/items/${bookId}`;
      const params = new HttpParams()
        .set('userId', this.userId.toString())
        .set('quantity', quantity.toString());
      this.http.put<Cart>(url, null, { params }).subscribe(
        (cart: Cart) => {
          this.cartSubject.next(cart);
        },
        (error) => {
          console.log('Error updating quantity:', error);
        }
      );
    } else {
      console.log('User ID is not available');
    }
  }

  removeFromCart(bookId: number) {
    if (this.userId) {
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
  }

  clearCart() {
    if (this.userId) {
      const url = `${this.apiUrl}`;
      const params = {
        userId: this.userId
      };
      this.http.delete<Cart>(url, {params}).subscribe(
        (cart: Cart) => {
          this.cartSubject.next(cart);
        },
        (error) => {
          console.log('Error clearing cart:', error);
        }
      );
    }
  }

  checkout(): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/checkout`, null);
  }
}
