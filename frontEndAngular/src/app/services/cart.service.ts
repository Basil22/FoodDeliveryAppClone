import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cart } from '../models/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  

  private apiUrl = 'http://localhost:8084/api/carts';  // URL of your backend API endpoint

  constructor(private http: HttpClient) { }

  // Add an item to the cart
  addItemToCart(userId: number, vendorId: number, payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/add/${userId}/${vendorId}`, payload);
  }
  getCart(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/user/${userId}`); // Update this to your endpoint
  }
  updateCartWithCoupon(cartId: number, couponType: string): Observable<Cart> {
    return this.http.put<Cart>(`${this.apiUrl}/applyCoupon/${cartId}?couponType=${couponType}`, {});
  }
  updateCart(cartId: number, userId: number, newItemQuantities: { [itemName: string]: number }, vendorId: number): Observable<Cart> {
    return this.http.put<Cart>(`${this.apiUrl}/updateCart`, { cartId, userId, newItemQuantities, vendorId });
  }
}
