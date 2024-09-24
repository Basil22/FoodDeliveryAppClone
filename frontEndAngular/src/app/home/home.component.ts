import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';
import { catchError, of, tap } from 'rxjs';
import { Vendor } from '../models/vendor';

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
  constructor(private itemService: ItemService) {}

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
}
