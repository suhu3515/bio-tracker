package com.example.biotracker;

public class Orders {

    int order_id;
    String product_name, product_image, order_qty, order_amount, payment_mode, order_address, pay_status, order_status, order_date, estimated_time;

    public Orders(int order_id, String product_name, String product_image, String order_qty, String order_amount, String payment_mode, String order_address, String pay_status, String order_status, String order_date, String estimated_time) {
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.order_qty = order_qty;
        this.order_amount = order_amount;
        this.payment_mode = payment_mode;
        this.order_address = order_address;
        this.pay_status = pay_status;
        this.order_status = order_status;
        this.order_date = order_date;
        this.estimated_time = estimated_time;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getOrder_qty() {
        return order_qty;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getOrder_address() {
        return order_address;
    }

    public String getPay_status() {
        return pay_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public String getOrder_date() {
        return order_date;
    }
}
