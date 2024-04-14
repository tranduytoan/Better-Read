import {Component, OnInit} from '@angular/core';
import {CartService} from "../../core/services/cart.service";
import {Cart} from "../../shared/models/cart";
import {UserService} from "../../core/services/user.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{
  cart$ = this.cartService.cart$
  userId!: number | null;
  constructor(private cartService: CartService, private userService: UserService) {
  }
  ngOnInit(): void {
      this.cartService.loadCart();
      this.userService.userId$.subscribe(userId => {
        this.userId = userId;
     })
    this.cart$.subscribe(cart => {
      console.log(cart);
      if (cart.cartItems.length > 0) {
        console.log('First item bookId:', cart.cartItems[0].bookId);
      }
    });

  }

  getSelectedItemsTotal(cart: Cart) {
    return cart.cartItems
      .filter(item => item.selected)
      .reduce((total, item) => total + item.price * item.quantity, 0);
  }

  updateQuantity(bookId: number, quantity: number): void {
    if (bookId == null) {
      console.error('Book ID is undefined');
      return;
    }
    this.cartService.updateQuantity(bookId, quantity);
  }


  removeFromCart(bookId: number): void {
    this.cartService.removeFromCart(bookId);
  }

  clearCart(): void {
    this.cartService.clearCart();
  }

  checkout() {

  }
}
