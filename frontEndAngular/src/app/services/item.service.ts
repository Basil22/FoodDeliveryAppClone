import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Item } from '../models/item';
@Injectable({
  providedIn: 'root',
})
export class ItemService {
  getItemsByName(searchTerm: string) {
    throw new Error('Method not implemented.');
  }
  private apiUrl = 'http://localhost:8080/api/vendor'; // URL of your backend API endpoint

  constructor(private http: HttpClient) {}

  // Method to get all items
  getAllItems(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl + '/allItems');
  }

  getItemsOfVendorByName(vendorName: string): Observable<Item[]> {
    console.log('Vendor Name:', vendorName); // Debugging line
    const encodedVendorName = encodeURIComponent(vendorName);
    return this.http.get<any>(`${this.apiUrl}/${encodedVendorName}/items/all`);
  }
}
