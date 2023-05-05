package com.hamza.printingpress.Models;

public class CustomerPrintRequest {
    int id;
    String product_size;
    int quantity;
    String paper_quality;
    String color_scheme;
    String cost;
    String phone_number;
    String design;
    String status;
    String shop_name;
    String shop_contact;

    public CustomerPrintRequest(int id, String product_size, int quantity, String paper_quality, String color_scheme, String cost, String phone_number, String design, String status, String shop_name, String shop_contact) {
        this.id = id;
        this.product_size = product_size;
        this.quantity = quantity;
        this.paper_quality = paper_quality;
        this.color_scheme = color_scheme;
        this.cost = cost;
        this.phone_number = phone_number;
        this.design = design;
        this.status = status;
        this.shop_name = shop_name;
        this.shop_contact = shop_contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaper_quality() {
        return paper_quality;
    }

    public void setPaper_quality(String paper_quality) {
        this.paper_quality = paper_quality;
    }

    public String getColor_scheme() {
        return color_scheme;
    }

    public void setColor_scheme(String color_scheme) {
        this.color_scheme = color_scheme;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_contact() {
        return shop_contact;
    }

    public void setShop_contact(String shop_contact) {
        this.shop_contact = shop_contact;
    }
}
