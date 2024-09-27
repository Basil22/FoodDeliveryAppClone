import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Item } from '../models/item';
import { Vendor } from '../models/vendor';
import { ItemService } from '../services/item.service';
import { VendorService } from '../services/vendor-service.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  @ViewChild('itemDetailsModal') itemDetailsModal!: ElementRef;
  items: Item[] = []; // Array to hold the items fetched from backend
  searchTerm: string = '';
  vendors: Vendor[] = [];

  isItemSearch: boolean = true;
  localQuantities: { [key: string]: number } = {}; // Object to store quantities per item
  quantityControlVisible: { [key: string]: boolean } = {};
  selectedItemName: string | any = null;
  // Variables to store selected item and vendor details
  selectedItem: any = null;
  selectedVendor: any = null;

  constructor(
    private itemService: ItemService,
    private vendorService: VendorService
  ) {}

  ngOnInit(): void {
    this.getItems();
    this.getVendors();
  }

  getItems(): void {
    this.itemService.getAllItems().subscribe((data: Item[]) => {
      this.items = data;

      this.items.forEach((item) => {
        this.quantityControlVisible[item.itemName] = false;
      });
    });
  }

  getVendors(): void {
    this.vendorService.getAllVendors().subscribe((data: Vendor[]) => {
      this.vendors = data;
      //console.log('Fetched Vendors:', this.vendors);
    });
  }

  increaseQuantity(item: Item) {
    this.localQuantities[item.itemName] =
      (this.localQuantities[item.itemName] || 0) + 1;
    //console.log(`Quantity for ${item.itemName} increased to: ${this.localQuantities[item.itemName]}`);
  }

  decreaseQuantity(item: Item) {
    if ((this.localQuantities[item.itemName] || 0) > 0) {
      this.localQuantities[item.itemName]--;
      //console.log(`Quantity for ${item.itemName} decreased to: ${this.localQuantities[item.itemName]}`);
    }
  }

  addToCart(item: Item) {
    // Set the quantity to 1 if it hasn't been set yet
    if (!this.localQuantities[item.itemName]) {
      this.localQuantities[item.itemName] = 1;
    }
    const quantity = this.localQuantities[item.itemName] || 0;
    console.log(`Added ${quantity} of ${item.itemName} to the cart.`);
    // Add your cart logic here
  }

  searchItemsOrVendors(): void {
    // First, attempt to search for items by name
    this.itemService.getItemDetails(this.searchTerm).subscribe({
      // If items are found, display them

      next: (items: Item[]) => {
        if (items.length > 0) {
          this.items = items;
          this.isItemSearch = true; // Set flag to display items
          console.log('Items found:', items);
        } else {
          // If no items are found, search for vendors by name
          this.searchVendors();
        }
      },

      // If the item search fails, fallback to searching vendors
      error: (err) => {
        console.error('Error fetching items:', err);
        this.searchVendors();
      },
    });
  }

  searchVendors(): void {
    this.itemService.getItemsOfVendorByName(this.searchTerm).subscribe({
      next: (data: Item[]) => {
        if (data.length > 0) {
          this.items = data;
          this.isItemSearch = true; // Display items if found
          console.log('Items found:', data);
        } else {
          // If no items found, search for vendors
          this.searchVendors();
        }
      },
      error: (err) => {
        console.error('Error fetching items:', err);
        // Fallback to search vendors if item search fails
        this.searchVendors();
      },
    });
  }

  // New method: Handle item click to show item and corresponding vendor details
  showItemDetails(item: Item): void {
    if (!item || !item.itemName) {
      console.error('Invalid item or itemName');
      return;
    }

    this.selectedItem = item;
    this.vendorService.getVendorsByItemName(item.itemName).subscribe({
      next: (vendors: Vendor[]) => {
        this.selectedVendor = vendors.length > 0 ? vendors[0] : null;
      },
      error: (err) => {
        console.error('Error fetching vendor for item:', err);
      },
    });
  }

  getVendorName(vendorId: number): string {
    const vendor = this.vendors.find((v) => v.vendorId === vendorId);
    return vendor ? vendor.vendorName : 'UnKnown Vendor';
  }

  getItemImageUrl(itemName: string): string {
    if (!itemName) {
      return 'assets/images/default-item-image.jpg'; // return a default image if itemName is null or undefined
    } else {
      return `/assets/images/${itemName.toLowerCase().replace(/ /g, '-')}.png`;
    }
  }
}
