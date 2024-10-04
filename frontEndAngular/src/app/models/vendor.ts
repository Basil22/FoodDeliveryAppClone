import { Item } from "./item";

export interface Vendor {
  vendorId: number;
  vendorName: string;

  fssaiLicenseNumber: string;
  
  itemList: Item[]; 
  open: boolean; 
  address: string; 
  contactNumber: string;// Assuming 'Item' interface exists for itemList
}
