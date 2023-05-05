package com.hamza.printingpress.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.printingpress.Models.ShopAdmin;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.function.Function;

public class UserAuth {
    public static void loginUser(Context context, String email, String password, Function<Boolean, Void> callback) {
        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
            data.put("password", password);

            VolleyApiRequest.objectPost(context, URLs.shopAdminAuthUrl, data, e -> {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
                callback.apply(false);
                return  null;
            }, response -> {
                try {
                    if(response.getBoolean("success")) {
                        //Shop admin is ok save to preferences
                        JSONObject shop = response.getJSONObject("shop");
                        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.putBoolean("shop_admin_login", true);
                        ShopAdmin admin = new ShopAdmin(shop.getInt("id"),
                                shop.getString("name"),
                                shop.getString("email"),
                                shop.getString("password"),
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
                        Type adminType = new TypeToken<ShopAdmin>() {}.getType();
                        Gson gson = new Gson();
                        String serializedShop = gson.toJson(admin, adminType);
                        editor.putString("shop", serializedShop);
                        editor.apply();

                        Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        callback.apply(true);
                    } else {
                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        callback.apply(false);
                    }
                } catch (JSONException ignore) {
                    Toast.makeText(context, "Login failed! Data exception occurred", Toast.LENGTH_SHORT).show();
                    callback.apply(false);
                }
                return null;
            });

        } catch (JSONException e) {
            e.printStackTrace();
            callback.apply(false);
        }


    }

    public static boolean isShopAdminLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return preferences.getBoolean("shop_admin_login", false);
    }

    public static String getShopToken(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            String shop = preferences.getString("shop", "");
            Type adminType = new TypeToken<ShopAdmin>() {
            }.getType();
            Gson gson = new Gson();
            ShopAdmin shopAdmin = gson.fromJson(shop, adminType);
            return "Bearer " + shopAdmin.getId();
        }
        catch(Exception ignore) {
            return "";
        }
    }

    public static ShopAdmin getAuthShop(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            String shop = preferences.getString("shop", "");
            Type adminType = new TypeToken<ShopAdmin>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(shop, adminType);
        }
        catch(Exception ignore) {
            return null;
        }
    }

    public static void logout(Context context) {
        try{
            SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }catch(Exception ignore){

        }
    }
}
