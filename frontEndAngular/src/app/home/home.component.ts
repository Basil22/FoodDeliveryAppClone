import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { Vendor } from '../models/vendor';
import { CartService } from '../services/cart.service';
import { ItemService } from '../services/item.service';
import { UserService } from '../services/user.service';
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
  @ViewChild('vendorSection') vendorSection!: ElementRef;
  items: Item[] = [];
  searchTerm: string = '';
  vendors: Vendor[] = [];
  isVendorSearch: boolean = true;
  localQuantities: { [key: string]: number } = {};
  quantityControlVisible: { [key: string]: boolean } = {};
  selectedItemName: string | any = null;
  selectedItem: any = null;
  selectedVendor: any = null;
  router: any;

  constructor(
    private itemService: ItemService,
    private vendorService: VendorService,
    private userService: UserService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getAllVendors();
  }

  getItems(): void {
    this.itemService.getAllItems().subscribe((data: Item[]) => {
      this.items = data;
      this.items.forEach((item) => {
        this.quantityControlVisible[item.itemName] = false;
      });
    });
  }

  getAllVendors(): void {
    this.vendorService.getAllVendors().subscribe((data: Vendor[]) => {
      this.vendors = data;

      setTimeout(() => {
        if (this.vendorSection) {
          this.vendorSection.nativeElement.scrollIntoView({
            behavior: 'smooth',
          });
        }
      }, 300);
    });
  }

  // Check if the user is logged in by verifying if 'userId' exists in localStorage
  getUserId(): string | null {
    return localStorage.getItem('userId'); // Returns true if 'userId' exists
  }

  // Modify addToCart to check if the user is logged in

  addToCart(item: Item): void {
    const userIdString = this.getUserId(); // Get userId as string
    const userIdtoNumber = userIdString ? Number(userIdString) : NaN; // Convert to number
    console.log('Retrieved userId from localStorage:', userIdString); // Debug log
    if (isNaN(userIdtoNumber)) {
      // Check if userId is NaN
      alert('Please log in to add items to the cart.');
      return;
    }

    // Check if the quantity is already set, otherwise default to 1
    if (!this.localQuantities[item.itemName]) {
      this.localQuantities[item.itemName] = 1;
    }

    const quantity = this.localQuantities[item.itemName]; // Get current quantity
    const itemName = item.itemName; // Assuming the item object has a name property
    const vendorId = Number(item.vendorId); // Get vendorId from item.vendorId; // Assuming the item object has a vendorId property

    // After user selects a restaurant/vendor

    if (!vendorId || isNaN(vendorId)) {
      console.error('Vendor ID is invalid:', vendorId);
      return; // Exit if vendorId is invalid
    }

    // Store vendorId in localStorage
    localStorage.setItem('vendorId', vendorId.toString());

    const cartPayload = {
      itemQuantities: { [itemName]: quantity }, // Create a map where itemName is key, quantity is value
    };

    // Call the CartService to add the item to the cart
    this.cartService
      .addItemToCart(userIdtoNumber, vendorId, cartPayload)
      .subscribe({
        next: (response) => {
          console.log('Item added to cart successfully:', response);
          // After user selects a restaurant/vendor
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 0) {
            console.error('A network or CORS error occurred:', error.error);
          } else if (error.status === 500) {
            // Check if the error is in text form instead of JSON
            const errorText =
              error.error instanceof Object
                ? JSON.stringify(error.error)
                : error.error;
            console.error(
              `Backend returned code ${error.status}, message was: ${errorText}`
            );
          } else {
            console.error(
              `Backend returned code ${error.status}, body was: ${error.error}`
            );
          }
        },
      });
  }

  increaseQuantity(item: Item) {
    this.localQuantities[item.itemName] =
      (this.localQuantities[item.itemName] || 0) + 1;
  }

  decreaseQuantity(item: Item) {
    if ((this.localQuantities[item.itemName] || 0) > 0) {
      this.localQuantities[item.itemName]--;
    }
  }

  showItemDetails(item: Item): void {
    if (!item || !item.itemName) {
      console.error('Invalid item or itemName');
      this.items = [];
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
    return vendor ? vendor.vendorName : 'Unknown Vendor';
  }

  getItemImageUrl(itemName: string): string {
    if (!itemName) {
      return 'assets/images/question-mark.jpg';
    }

    const formattedPath = itemName.toLowerCase().replace(/ /g, '-');
    const imagePath = `assets/images/${formattedPath}.png`;

    const img = new Image();
    img.src = imagePath;
    if (!img.complete) {
      return 'assets/images/question-mark.jpg';
    }

    return imagePath;
  }
  getRestaurantImageUrl(vendorName: string): string {
    return `/assets/images/${vendorName.toLowerCase().replace(/ /g, '-')}.png`;
  }
}
