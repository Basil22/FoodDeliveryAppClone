import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
import { Vendor } from '../models/vendor';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private apiUrl = 'http://localhost:8080/api/vendor'; // Your backend API endpoint for vendors

  constructor(private http: HttpClient) {}

  // Method to fetch vendors by item name
  getVendorsByItemName(itemName: string): Observable<Vendor[]> {
    const encodedItemName = encodeURIComponent(itemName);
    return this.http.get<Vendor[]>(`${this.apiUrl}/items/${encodedItemName}`);
  }

  addVendorDetails(vendor: Vendor): Observable<Vendor> {
    return this.http.post<Vendor>(`${this.apiUrl}/add`, vendor);
  }

  getAllVendors(): Observable<Vendor[]> {
    return this.http.get<Vendor[]>(`${this.apiUrl}/all`);
  }

  getItemsOfVendor(vendorId: number): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.apiUrl}/${vendorId}/items`);
  }
}
