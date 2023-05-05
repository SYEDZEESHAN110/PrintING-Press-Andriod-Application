package com.hamza.printingpress.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopAdmin {
    int id;
    String name;
    String email;
    String password;
    String gender;
    String phone_number;
    String shop_name;
    String shop_city;
    String shop_address;
    double shop_lat;
    double shop_lon;
    String easypaisa_number;
    String jazzcash_number;
    String account_title;
    String account_number;
    String bank_name;

    public ShopAdmin(int id, String name, String email, String password, String gender, String phone_number, String shop_name, String shop_city, String shop_address, double shop_lat, double shop_lon, String easypaisa_number, String jazzcash_number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone_number = phone_number;
        this.shop_name = shop_name;
        this.shop_city = shop_city;
        this.shop_address = shop_address;
        this.shop_lat = shop_lat;
        this.shop_lon = shop_lon;
        this.easypaisa_number = easypaisa_number;
        this.jazzcash_number = jazzcash_number;
        this.account_title = "";
        this.account_number = "";
        this.bank_name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_city() {
        return shop_city;
    }

    public void setShop_city(String shop_city) {
        this.shop_city = shop_city;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public double getShop_lat() {
        return shop_lat;
    }

    public void setShop_lat(double shop_lat) {
        this.shop_lat = shop_lat;
    }

    public double getShop_lon() {
        return shop_lon;
    }

    public void setShop_lon(double shop_lon) {
        this.shop_lon = shop_lon;
    }

    public String getEasypaisa_number() {
        return easypaisa_number;
    }

    public void setEasypaisa_number(String easypaisa_number) {
        this.easypaisa_number = easypaisa_number;
    }

    public String getJazzcash_number() {
        return jazzcash_number;
    }

    public void setJazzcash_number(String jazzcash_number) {
        this.jazzcash_number = jazzcash_number;
    }

    public String getAccount_title() {
        return account_title;
    }

    public void setAccount_title(String account_title) {
        this.account_title = account_title;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public static ShopAdmin extractThisFromJSON(JSONObject shop) {
        try {
            ShopAdmin shop_admin = new ShopAdmin(shop.getInt("id"),
                    shop.getString("name"),
                    shop.getString("email"),
                    "",
                    shop.getString("gender"),
                    shop.getString("phone_number"),
                    shop.getString("shop_name"),
                    shop.getString("shop_city"),
                    shop.getString("shop_address"),
                    shop.getDouble("shop_lat"),
                    shop.getDouble("shop_lon"),
                    shop.getString("easypaisa_number"),
                    shop.getString("jazzcash_number")
            );
            try {
                shop_admin.setAccount_number(shop.getString("account_number"));
                shop_admin.setAccount_title(shop.getString("account_title"));
                shop_admin.setBank_name(shop.getString("bank_name"));
            }catch(Exception ignore){}

            return shop_admin;
        } catch (JSONException ignore) {
            return null;
        }
    }
}
