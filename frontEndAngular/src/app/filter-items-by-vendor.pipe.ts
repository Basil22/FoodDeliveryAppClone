import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterItemsByVendor',
  standalone: true
})
export class FilterItemsByVendorPipe implements PipeTransform {
  transform(items: any[], vendorId: number): any[] {
    if (!items || !vendorId) {
      return items; // Return all items if no vendorId is provided
    }
    
    // Filter items by vendorId
    return items.filter(item => item.vendorId === vendorId);
  }

}
