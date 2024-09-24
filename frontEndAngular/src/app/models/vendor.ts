import { Item } from './item';

export interface Vendor {
  vendorId: number;
  vendorName: string;
  vendorContactNumber: string;
  vendorAddress: string;
  fssaiLicenseNumber: string;
  isOpen: boolean;
  itemList: Item[];
}
