import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  

  private apiUrl = 'http://localhost:8080/api/carts';  // URL of your backend API endpoint

  constructor(private http: HttpClient) { }

  // Add an item to the cart
  addItemToCart(cartPayload: { userId: number, itemQuantities: { [itemName: string]: number }, vendorId: number }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add/${cartPayload.userId}/${cartPayload.vendorId}`, cartPayload);
  }
}
