export interface Cart {
    id: number;
    userId: number;
    itemQuantities: { [itemName: string]: number };
    totalPrice: number;
    gstIncludedPrice: number;
    deliveryCharges: number;
    vendorName: string;
    payableAmount: number;
    coupon: string;  // Represent CouponType as string
    discountedPrice: number;
    hasCutlery: boolean;
}
