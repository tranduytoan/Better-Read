import {CartItem} from "./cartItem";

export interface Cart {
  id: number;
  userId: number;
  createdAt: Date;
  cartItems: CartItem[];
}
