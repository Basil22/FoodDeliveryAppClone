import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Item } from '../models/item';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  items: Item[] = [];  // Array to hold the items fetched from backend
  searchTerm: string = '';
  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
    this.getItems();
  }

  getItems(): void {
    this.itemService.getAllItems().subscribe((data: any[]) => {
      this.items = data;
    });
  }
  searchRestaurants() {
    this.itemService.getItemsOfVendorByName(this.searchTerm).subscribe(
      (data: any) => {
        this.items = data; // Update items with the search results
      },
      (  error: any) => {
        console.error('Error fetching restaurants:', error);
      }
    );
  }
  
  getImageUrl(itemName: string): string {
    return `/assets/images/${itemName.toLowerCase().replace(/ /g, '-')}.png`;
  }
  
  
  
}
