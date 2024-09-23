package PaymentService.DTO;

public enum CouponType {
    DISCOUNT_10("DIS10", 10.0),  // 10% discount
    DISCOUNT_20("DIS20", 20.0),  // 20% discount
    FLAT_50("FLAT50", 50.0);     // Flat $50 discount
 
    private final String code;
    private final double discountValue;
 
    CouponType(String code, double discountValue) {
        this.code = code;
        this.discountValue = discountValue;
    }
 
    public String getCode() {
        return code;
    }
 
    public double getDiscountValue() {
        return discountValue;
    }
 
    public static CouponType fromCode(String code) {
        for (CouponType coupon : values()) {
            if (coupon.getCode().equalsIgnoreCase(code)) {
                return coupon;
            }
        }
        throw new IllegalArgumentException("Invalid coupon code: " + code);
    }
}