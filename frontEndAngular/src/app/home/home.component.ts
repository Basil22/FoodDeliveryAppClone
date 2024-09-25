import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { tap } from 'rxjs';
import { Item } from '../models/item';
import { Vendor } from '../models/vendor';
import { ItemService } from '../services/item.service';
import { VendorService } from '../services/vendor-service.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  @ViewChild('itemDetailsModal') itemDetailsModal!: ElementRef;
  items: Item[] = []; // Array to hold the items fetched from backend
  searchTerm: string = '';
  vendors: Vendor[] = [];
  isItemSearch: boolean = true;
  localQuantities: { [key: string]: number } = {}; // Object to store quantities per item
  quantityControlVisible: { [key: string]: boolean } = {};
  selectedItemName: string | null = null;
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
      console.log('Fetched Items:', this.items);
      // Initialize visibility state for quantity controls
      this.items.forEach(item => {
        this.quantityControlVisible[item.itemName] = false;
      });
    });
  }
 
  getVendors(): void {
    this.vendorService.getAllVendors().subscribe((data: Vendor[]) => {
      this.vendors = data;
      console.log('Fetched Vendors:', this.vendors);
    });
  }
 
  increaseQuantity(item: Item) {
    this.localQuantities[item.itemName] = (this.localQuantities[item.itemName] || 0) + 1;
    console.log(`Quantity for ${item.itemName} increased to: ${this.localQuantities[item.itemName]}`);
  }
 
  decreaseQuantity(item: Item) {
    if ((this.localQuantities[item.itemName] || 0) > 0) {
      this.localQuantities[item.itemName]--;
      console.log(`Quantity for ${item.itemName} decreased to: ${this.localQuantities[item.itemName]}`);
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
 
  // Search items or vendors based on search term
  searchItemsOrVendors(): void {
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
 
  // Method to search for vendors based on the search term
  searchVendors(): void {
    this.vendorService.getVendorsByItemName(this.searchTerm).subscribe({
      next: (data: Vendor[]) => {
        this.vendors = data;
        this.isItemSearch = false;
      },
      error: (err) => {
        console.error('Error fetching vendors', err);
      },
      complete: () => {
        console.log('Vendor fetch complete');
      },
    });
  }
 
  // New method: Handle item click to show item and corresponding vendor details
  showItemDetails(item: Item): void {
    this.selectedItem = item;
    this.vendorService.getVendorsByItemName(item.itemName).subscribe({
      next: (vendors: Vendor[]) => {
        // Assuming the first vendor in the response is the corresponding vendor for the item
        this.selectedVendor = vendors.length > 0 ? vendors[0] : null;
        console.log('Selected Vendor:', this.selectedVendor);
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
    }
    return `/assets/images/${itemName.toLowerCase().replace( / /g, '-')}.png`;
  }
  
  
  getVendorImage(vendorName: string): string {
    const vendor = this.vendors.find((v) => v.vendorName === vendorName);
    if (vendor && vendor.itemList && vendor.itemList.length > 0) {
      // Take the first item in the vendor's item list and use its name to construct the image URL
      const firstItem = vendor.itemList[0];
      const formattedItemName = firstItem.itemName.toLowerCase().replace(/\s+/g, '-');
      return `assets/images/${formattedItemName}.png`;
    }
    // If the vendor has no items, return a default image
    return `assets/images/default.png`;
  }
}