package com.hamza.printingpress.Models;

import org.json.JSONObject;

public class ShopPrintRequest {
    public int id;
    String type;
    String product_size;
    int quantity;
    String paper_quality;
    String color_scheme;
    String cost;
    String phone_number;
    String payment_method;
    String design;
    boolean status;

    public ShopPrintRequest(int id, String type, String product_size, int quantity, String paper_quality, String color_scheme, String cost, String phone_number, String payment_method, String design, boolean status) {
        this.id = id;
        this.type = type;
        this.product_size = product_size;
        this.quantity = quantity;
        this.paper_quality = paper_quality;
        this.color_scheme = color_scheme;
        this.cost = cost;
        this.phone_number = phone_number;
        this.payment_method = payment_method;
        this.design = design;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static ShopPrintRequest convertJsonToThisDone(JSONObject request) {
        try {
            int id = request.getInt("id");
            String type = request.getString("type");
            String paper_quality = request.getString("paper_quality");
            String size = request.getString("size");
            int quantity = request.getInt("quantity");
            String color_scheme = request.getString("color_scheme");
            String phone_number = request.getString("phone_number");
            String design = request.getString("design");
            String payment_method = request.getString("payment_method");
            boolean is_done = true;

            double rgbCost = request.getJSONObject("extra_cost_database_row").getDouble("RGB");
            double cmykCost = request.getJSONObject("extra_cost_database_row").getDouble("CMYK");
            double grayscaleCost = request.getJSONObject("extra_cost_database_row").getDouble("grayscale");

            double cost = 0;
            if(color_scheme.equalsIgnoreCase("rgb")) {
                cost = rgbCost * quantity;
            }
            if(color_scheme.equalsIgnoreCase("cmyk")) {
                cost = cmykCost * quantity;
            }
            if(color_scheme.equalsIgnoreCase("grayscale")) {
                cost = grayscaleCost * quantity;
            }

            return new ShopPrintRequest(id, type, size, quantity, paper_quality, color_scheme, String.valueOf(cost), phone_number, payment_method, design, is_done);

        }catch(Exception ignore){}

        return null;
    }

    public static ShopPrintRequest convertJsonToThis(JSONObject request) {
        try {
            int id = request.getInt("id");
            String type = request.getString("type");
            String paper_quality = request.getString("paper_quality");
            String size = request.getString("size");
            int quantity = request.getInt("quantity");
            String color_scheme = request.getString("color_scheme");
            String phone_number = request.getString("phone_number");
            String design = request.getString("design");
            String shop_id = request.getString("shop_id");
            String payment_method = request.getString("payment_method");
            boolean is_done = false;

            double rgbCost = request.getJSONObject("extra_cost_database_row").getDouble("RGB");
            double cmykCost = request.getJSONObject("extra_cost_database_row").getDouble("CMYK");
            double grayscaleCost = request.getJSONObject("extra_cost_database_row").getDouble("grayscale");

            double cost = 0;
            if(color_scheme.equalsIgnoreCase("rgb")) {
                cost = rgbCost * quantity;
            }
            if(color_scheme.equalsIgnoreCase("cmyk")) {
                cost = cmykCost * quantity;
            }
            if(color_scheme.equalsIgnoreCase("grayscale")) {
                cost = grayscaleCost * quantity;
            }

            return new ShopPrintRequest(id, type, size, quantity, paper_quality, color_scheme, String.valueOf(cost), phone_number, payment_method, design, is_done);

        }catch(Exception ignore){}

        return null;
    }
}
