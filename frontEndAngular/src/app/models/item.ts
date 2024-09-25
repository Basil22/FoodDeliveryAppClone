
export interface Item {
    itemId: number;                // Unique identifier for the item
    itemName: string;              // Name of the item
    category: 'breakfast' | 'lunch' | 'dinner' | 'sides' | 'drinks'; // Category of the item
    price: number;             // Price of the item
    description: string;       // Description of the item
    ratings: number;           // Ratings of the item (0.0 to 5.0)
    available: boolean;          // Availability of the item
    vendorId: number; 
   
  }
  
