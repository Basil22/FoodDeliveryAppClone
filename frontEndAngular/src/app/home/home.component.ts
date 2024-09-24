import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';
import { Vendor } from '../models/vendor';
import { VendorService } from '../services/vendor.service';
import { tap } from 'rxjs';

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
    this.getVendors();
  }
  getItems(): void {
    this.itemService.getAllItems().subscribe((data: any[]) => {
      this.items = data;
    });
  }

  getVendors(): void {
    //Display all vendors not items.
    this.vendorService.getAllVendors().subscribe((data: any[]) => {
      this.vendors = data;
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
    this.vendorService
      .getVendorsByItemName(this.searchTerm)
      .pipe(tap((data: any) => (this.items = data)))
      .subscribe();
    (error: any) => {
      console.log('Error fetching items:', error);
    };
  }

  getItemImageUrl(itemName: string): string {
    return `/assets/images/${itemName.toLowerCase().replace(/ /g, '-')}.png`;
  }

  getVendorImage(vendorName: string): string {
    const vendor = this.vendors.find((v) => v.vendorName === vendorName);

    if (vendor && vendor.itemList && vendor.itemList.length > 0) {
      const firstItem = vendor.itemList[0];
      const formattedItemName = firstItem.itemName
        .toLowerCase()
        .replace(/\s+/g, '-');
      return `assets/images/${formattedItemName}.png`;
    }

    return `assets/images/default.png`;
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
