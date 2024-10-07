import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cart } from '../models/cart';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cart: Cart[] = [];
  gstRate: number = 0.18;
  deliveryCharges: number = 50;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    // Fetch the cart data from CartService
    
  }


}