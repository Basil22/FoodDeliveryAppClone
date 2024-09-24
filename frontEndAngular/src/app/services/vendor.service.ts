import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vendor } from '../models/vendor';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private apiUrl = 'http://localhost:8080/api/vendor'; // Your backend API endpoint for vendors

  constructor(private http: HttpClient) {}

  // Method to fetch vendors by item name
  getVendorsByItemName(itemName: string): Observable<Vendor[]> {
    console.log('Item Name:', itemName);
    const encodedItemName = encodeURIComponent(itemName);
    return this.http.get<Vendor[]>(`${this.apiUrl}/items/${encodedItemName}`);
  }

  getAllVendors(): Observable<Vendor[]> {
    return this.http.get<Vendor[]>(`${this.apiUrl}/all`);
  }
}
