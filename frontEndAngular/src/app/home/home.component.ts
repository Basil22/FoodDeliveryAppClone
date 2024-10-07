import { CommonModule } from '@angular/common';
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
  items: Item[] = [];
  searchTerm: string = '';
  vendors: Vendor[] = [];
  isItemSearch: boolean = true;
  localQuantities: { [key: string]: number } = {};
  quantityControlVisible: { [key: string]: boolean } = {};
  selectedItemName: string | any = null;
  selectedItem: any = null;
  selectedVendor: any = null;
 
  constructor(
    private itemService: ItemService,
    private vendorService: VendorService,
    private userService: UserService,
    private cartService: CartService
 
  ) {}
 
  ngOnInit(): void {
    this.getItems();
    this.getVendors();
  }
 
  getItems(): void {
    this.itemService.getAllItems().subscribe((data: Item[]) => {
      this.items = data;
      this.items.forEach(item => {
        this.quantityControlVisible[item.itemName] = false;
      });
    });
  }
 
  getVendors(): void {
    this.vendorService.getAllVendors().subscribe((data: Vendor[]) => {
      this.vendors = data;
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
  if (isNaN(userIdtoNumber)) { // Check if userId is NaN
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

  const cartPayload = {
     userId: userIdtoNumber, // Pass the userId from the logged-in user
    itemQuantities: { [itemName]: quantity }, // Create a map where itemName is key, quantity is value
    vendorId: vendorId // Pass the vendorId
  };

  // Call the CartService to add the item to the cart
  this.cartService.addItemToCart(cartPayload).subscribe({
    next: (response) => {
      console.log('Item added to cart successfully:', response);
    },
    error: (error) => {
      console.error('Error adding item to cart:', error);
    }
  });
}


  increaseQuantity(item: Item) {
    this.localQuantities[item.itemName] = (this.localQuantities[item.itemName] || 0) + 1;
  }
 
  decreaseQuantity(item: Item) {
    if ((this.localQuantities[item.itemName] || 0) > 0) {
      this.localQuantities[item.itemName]--;
    }
  }
 
  
 
  searchItemsOrVendors(): void {
    
    if(!this.searchTerm){
      this.items=[];
      this.vendors=[];
      return;
    }

    this.itemService.getItemDetails(this.searchTerm).subscribe({
      next: (data: any) => {
        console.log('Data returned from API:', data); // Check if data is correct
  
        if (data && Array.isArray(data) && data.length > 0) {
          // If items are found, assign them and do not call searchVendors
          this.items = data; 
          this.isItemSearch = true; // Set flag to display items
          console.log('Items found:', data);
        } else if (typeof data === 'object' && data.itemName) {
          // If data is a single object (not an array), wrap it in an array
          this.items = [data];
          this.isItemSearch = true;
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
          this.isItemSearch = true;
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
      return 'assets/images/default-item-image.jpg';
    }
    return `/assets/images/${itemName.toLowerCase().replace(/ /g, '-')}.png`;
  }
  getRestaurantImageUrl(vendorName: string): string {
    return `/assets/images/${vendorName.toLowerCase().replace(/ /g, '-')}.png`;
  }

  selectVendor(vendor: Vendor): void {
    this.selectedVendor = vendor;
    this.vendorService.getItemsOfVendor(vendor.vendorId).subscribe((data: Item[]) => {
      this.items = data; // Update items to show items from the selected vendor
    })

    
  }
}
