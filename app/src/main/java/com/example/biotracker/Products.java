package com.example.biotracker;

public class Products {

    int productId,sellerId;
    String productName, productPrice, productQty,productDesc,productImage, sellerName;

    public Products(int productId, String productName, String productPrice, String productQty, String productDesc, String productImage, String sellerName, int sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productDesc = productDesc;
        this.productImage = productImage;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public int getProductId() {
        return productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductQty() {
        return productQty;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getSellerName() {
        return sellerName;
    }
}
