package com.hamza.printingpress.Api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class VolleyApiRequest {
    public static void objectGet(Context context, String uri, JSONObject data, Function<String, Boolean> errorCallback, Function<JSONObject, Boolean> successCallback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, uri, data, response -> {
            try {
                successCallback.apply(response);
            } catch (Exception e) {
                errorCallback.apply("Not able to get data");
            }
        }, error -> {
            errorCallback.apply("Server error occurred");
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context,
                        "Not Connected to Wifi",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context,
                        "Auth Failed",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context,
                        "Server Error",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context,
                        "Network Problem",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context,
                        "Parsing Problem",
                        Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }

    public static void arrayGet(Context context, String uri, JSONArray data, Function<String, Void> errorCallback, Function<JSONArray, Void> successCallback) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, uri, data, successCallback::apply, error -> {
            errorCallback.apply("Server error occurred");
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context,
                        "Not Connected to Wifi",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context,
                        "Auth Failed",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context,
                        "Server Error",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context,
                        "Network Problem",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context,
                        "Parsing Problem",
                        Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }

    public static void objectPost(Context context, String uri, JSONObject data, Function<String, Void> errorCallback, Function<JSONObject, Void> successCallback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, uri, data, successCallback::apply, error -> {
            errorCallback.apply("Server error occurred");
            error.printStackTrace();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context,
                        "Not Connected to Wifi",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context,
                        "Auth Failed",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context,
                        "Server Error",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context,
                        "Network Problem",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context,
                        "Parsing Problem",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("HTTP_Content_Type","application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }


    public static void authArrayGet(Context context, String uri, JSONArray data, Function<String, Void> errorCallback, Function<JSONArray, Void> successCallback) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, uri, data, successCallback::apply, error -> {
            errorCallback.apply("Server error occurred");
            error.printStackTrace();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context,
                        "Not Connected to Wifi",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context,
                        "Auth Failed",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context,
                        "Server Error",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context,
                        "Network Problem",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context,
                        "Parsing Problem",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("HTTP_Content_Type","application/json");
                params.put("Authorization", UserAuth.getShopToken(context));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }

    public static void authObjectPost(Context context, String uri, JSONObject data, Function<String, Void> errorCallback, Function<JSONObject, Void> successCallback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, uri, data, successCallback::apply, error -> {
            errorCallback.apply("Server error occurred");
            error.printStackTrace();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(context,
                        "Not Connected to Wifi",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(context,
                        "Auth Failed",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(context,
                        "Server Error",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(context,
                        "Network Problem",
                        Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(context,
                        "Parsing Problem",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("HTTP_Content_Type","application/json");
                params.put("Authorization", UserAuth.getShopToken(context));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonRequest);
    }


}
