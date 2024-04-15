import {Component, OnInit} from '@angular/core';
import {CartService} from "../../core/services/cart.service";
import {Cart} from "../../shared/models/cart";
import {UserService} from "../../core/services/user.service";
import {Order} from "../../shared/models/order";
import {Router} from "@angular/router";
import {CartItem} from "../../shared/models/cartItem";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../../shared/components/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{
  cart$ = this.cartService.cart$
  userId!: number | null;
  totalAmount: number = 0;
  constructor(private cartService: CartService,
              private userService: UserService,
              private router: Router,
              private dialog: MatDialog) {
  }
  ngOnInit(): void {
      this.cartService.loadCart();
      this.userService.userId$.subscribe(userId => {
        this.userId = userId;
     })
    this.cart$.subscribe(cart => {
      console.log(cart);
      if (cart.cartItems && cart.cartItems.length > 0) {
        console.log('First item bookId:', cart.cartItems[0].bookId);
      }
    });

  }

  getTotalSelectedItems(cart: Cart): number {
    return cart.cartItems.filter(item => item.selected).length;
  }

  getSelectedItemsTotal(cart: Cart) {
    return cart.cartItems
      .filter(item => item.selected)
      .reduce((total, item) => total + item.price * item.quantity, 0);
  }

  updateQuantity(bookId: number, quantity: number): void {
    if (quantity < 1) {
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
    this.cartService.checkout().subscribe(
      (response: any) => {
        console.log('Checkout successful:', response);
        this.router.navigate(['/order-confirmation']);
      },
      (error) => {
        console.log('Error during checkout:', error);
      }
    );
  }

  decreaseQuantity(item: CartItem): void {
    if (item.quantity === 1) {
      this.openConfirmationDialog(item);
    } else {
      this.updateQuantity(item.bookId, item.quantity - 1);
    }
  }

  openConfirmationDialog(item: CartItem): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      position: { top: '50%', left: '50%' },
      panelClass: 'custom-dialog-container',
      data: { message: `Are you sure you want to remove "${item.bookTitle}" from your cart?` }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.removeFromCart(item.bookId);
      }
    });
  }
}
