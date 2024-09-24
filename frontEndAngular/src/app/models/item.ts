
export interface Item {
    itemId: number;                // Unique identifier for the item
    itemName: string;              // Name of the item
    itemCategory: 'breakfast' | 'lunch' | 'dinner' | 'sides' | 'drinks'; // Category of the item
    itemPrice: number;             // Price of the item
    itemDescription: string;       // Description of the item
    itemRatings: number;           // Ratings of the item (0.0 to 5.0)
    isAvailable: boolean;          // Availability of the item
    vendorId: number; 
    imageUrl: string;             // ID of the associated vendor
  }
  
