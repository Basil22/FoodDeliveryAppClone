import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cart } from '../models/cart';
import { CartService } from '../services/cart.service';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'] // corrected styleUrl to styleUrls
})
export class CartComponent implements OnInit {
  cart: Cart | null = null;
  couponTypes = [
    { code: 'DISCOUNT_10', discountValue: 10 },
    { code: 'DISCOUNT_20', discountValue: 20 },
    { code: 'FLAT_50', discountValue: 50 }
  ];

  constructor(private cartService: CartService, private itemService: ItemService) { }

  ngOnInit(): void {
    const userId: number | null = this.getUserId();
    if (userId !== null) {
      this.loadCart(userId); // Load the cart if userId is valid
    } else {
      console.error('User ID is null, cannot fetch cart.');
    }
  }

  getUserId(): number | null {
    const userId = localStorage.getItem('userId'); // Replace 'userId' with the actual key used to store user ID
    return userId ? +userId : null; // Return userId as a number, or null if it doesn't exist
  }

  loadCart(userId: number): void {
    this.cartService.getCart(userId).subscribe({
      next: (response) => {
        this.cart = response;
      },
      error: (error) => {
        console.error('Error fetching cart:', error);
      }
    });
  }

  applyCoupon(couponCode: string): void {
    const cartId = this.cart?.id; // Assume you have the cart ID
    if (cartId) {
      this.cartService.updateCartWithCoupon(cartId, couponCode).subscribe(
        (updatedCart) => {
          this.cart = updatedCart; // Update the cart in the component
          // Refresh the displayed prices
        },
        (error) => {
          console.error('Error applying coupon:', error);
        }
      );
    }
  }

  updateCartItemQuantity(itemName: string, newQuantity: number): void {
    const cartId = this.cart?.id;
    const userId = this.getUserId();  // Retrieve userId from localStorage
    const vendorId = localStorage.getItem('vendorId'); // Retrieve vendorId from localStorage

    if (!cartId) {
      console.error('Cart ID is missing.');
      return;
    }

    if (userId === null) {
      console.error('User ID is null, cannot update cart.');
      return;
    }

    if (!vendorId) {
      console.error('Vendor ID is missing.');
      return;
    }

    // Create newItemQuantities map
    const newItemQuantities: { [key: string]: number } = {};
    newItemQuantities[itemName] = newQuantity;

    // Call updateCart service method
    this.cartService.updateCart(cartId, userId, newItemQuantities, +vendorId)
      .subscribe(
        (updatedCart) => {
          this.cart = updatedCart;
          console.log('Cart updated successfully:', updatedCart);
        },
        (error) => {
          console.error('Error updating cart:', error);
        }
      );
  }

  increaseQuantity(itemName: string): void {
    if (this.cart && this.cart.itemQuantities[itemName] != null) {
      const newQuantity = this.cart.itemQuantities[itemName] + 1;
      this.updateCartItemQuantity(itemName, newQuantity);
    }
  }

  decreaseQuantity(itemName: string): void {
    if (this.cart && this.cart.itemQuantities[itemName] > 1) {
      const newQuantity = this.cart.itemQuantities[itemName] - 1;
      this.updateCartItemQuantity(itemName, newQuantity);
    }
  }
}
