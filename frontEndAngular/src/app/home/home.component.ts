import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';
import { catchError, of, tap } from 'rxjs';
import { Vendor } from '../models/vendor';
import { VendorService } from '../services/vendor.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  items: Item[] = []; // Array to hold the items fetched from backend
  searchTerm: string = '';
  vendors: Vendor[] = [];
  isItemSearch: boolean = true;
  constructor(
    private itemService: ItemService,
    private vendorService: VendorService
  ) {}

  ngOnInit(): void {
    this.getItems();
  }

  getItems(): void {
    this.itemService.getAllItems().subscribe((data: any[]) => {
      this.items = data;
    });
  }
  searchRestaurants() {
    this.itemService
      .getItemsOfVendorByName(this.searchTerm)
      .pipe(tap((data: any) => (this.items = data)))
      .subscribe();
    (error: any) => {
      console.error('Error fetching restaurants:', error);
    };
  }

  searchItems() {
    this.itemService
      .getVendorsByItemName(this.searchTerm)
      .pipe(tap((data: any) => (this.items = data)))
      .subscribe();
    (error: any) => {
      console.log('Error fetching items:', error);
    };
  }

  getImageUrl(itemName: string): string {
    return `/assets/images/${itemName.toLowerCase().replace(/ /g, '-')}.png`;
  }

  searchItemsOrVendors(): void {
    this.itemService.getItemsOfVendorByName(this.searchTerm).subscribe(
      (data: Item[]) => {
        if (data.length > 0) {
          this.items = data;
          this.isItemSearch = true; // Flag that we are displaying items
        } else {
          this.searchVendors();
        }
      },
      (error) => {
        console.error('Error fetching items:', error);
        this.searchVendors(); // Fallback to search vendors if item search fails
      }
    );
  }

  searchVendors(): void {
    this.vendorService.getVendorsByItemName(this.searchTerm).subscribe(
      (data: Vendor[]) => {
        this.vendors = data;
        this.isItemSearch = false; // Flag that we are displaying vendors
      },
      (error) => {
        console.error('Error fetching vendors:', error);
      }
    );
  }
}
