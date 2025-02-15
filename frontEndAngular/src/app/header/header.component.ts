import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { Vendor } from '../models/vendor';
import { ItemService } from '../services/item.service';
import { CommonModule } from '@angular/common';
import { VendorService } from '../services/vendor-service.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  @ViewChild('itemDetailsModal') itemDetailsModal!: ElementRef;
  items: Item[] = [];
  searchTerm: string = '';
  vendors: Vendor[] = [];
  isVendorSearch: boolean = true;
  localQuantities: { [key: string]: number } = {};
  quantityControlVisible: { [key: string]: boolean } = {};
  selectedItemName: string | any = null;
  selectedItem: any = null;
  selectedVendor: any = null;
  isSearchExpanded = false;

  @Input() title: string = '';

  constructor(
    private itemService: ItemService,
    private vendorService: VendorService
  ) {}

  toggleSearch() {
    this.isSearchExpanded = !this.isSearchExpanded;
  }

  searchItemsOrVendors(): void {
    if (!this.searchTerm) {
      this.items = [];
      this.vendors = [];
      return;
    }

    this.itemService.getItemDetails(this.searchTerm).subscribe({
      next: (data: any) => {
        console.log('Data returned from API:', data); // Check if data is correct

        if (data && Array.isArray(data) && data.length > 0) {
          // If items are found, assign them and do not call searchVendors
          this.items = data;
          this.isVendorSearch = false; // Set flag to display items
          console.log('Items found:', data);
        } else if (typeof data === 'object' && data.itemName) {
          // If data is a single object (not an array), wrap it in an array
          this.items = [data];
          this.isVendorSearch = false;
          console.log('Single item found:', data);
        } else {
          // If no items found, proceed to search vendors
          console.log('No items found for:', this.searchTerm);
          this.searchVendors();
        }
      },
      error: (err) => {
        console.error('Error fetching items:', err);
        this.searchVendors();
      },
    });
  }

  searchVendors(): void {
    this.itemService.getItemsOfVendorByName(this.searchTerm).subscribe({
      next: (data: Item[]) => {
        console.log('Data returned from API:', data);
        if (data.length > 0) {
          this.items = data;
          this.isVendorSearch = false;
          this.selectedVendor = { vendorName: this.searchTerm } as Vendor;
        } else {
          console.error('No items found for the vendor.');
          this.items = [];
          this.selectedVendor = null;
        }
      },
      error: (err) => {
        console.error('Error fetching items for vendor', err);
        this.items = []; // Clear items on error
        this.selectedVendor = null;
      },
    });
  }

  selectVendor(vendor: Vendor): void {
    this.selectedVendor = vendor;
    this.isVendorSearch = false;
    this.vendorService
      .getItemsOfVendor(vendor.vendorId)
      .subscribe((data: Item[]) => {
        this.items = data; // Update items to show items from the selected vendor
      });
  }
}
