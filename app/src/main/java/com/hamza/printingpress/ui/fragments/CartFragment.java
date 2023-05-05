package com.hamza.printingpress.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Adapders.CartAdapter;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.CustomerPrintRequest;
import com.hamza.printingpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    ArrayList<CustomerPrintRequest> requests = new ArrayList<>();
    RecyclerView list;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requests = new ArrayList<>();
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_cart, container, false);
        list = v.findViewById(R.id.request_list);
        JSONArray savedRequests = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            SharedPreferences preferences = requireContext().getSharedPreferences("requests", Context.MODE_PRIVATE);
            savedRequests = new JSONArray(preferences.getString("orders", "[]"));
            data.put("data", savedRequests);
        }
        catch (Exception ignore) {}

        VolleyApiRequest.objectPost(getContext(), URLs.getRequestList, data, e -> {
            return null;
        }, response -> {

            try {
                if(response.getBoolean("success")) {
                    JSONArray requestArray = response.getJSONArray("data");
                    for (int i = 0; i < requestArray.length(); i++) {
                        int id = requestArray.getJSONObject(i).getInt("id");
                        String size = requestArray.getJSONObject(i).getString("size");
                        int quantity = requestArray.getJSONObject(i).getInt("quantity");
                        String paper_quality = requestArray.getJSONObject(i).getString("paper_quality");
                        String color_scheme = requestArray.getJSONObject(i).getString("color_scheme");
                        String cost = requestArray.getJSONObject(i).getString("cost");
                        String phone_number = requestArray.getJSONObject(i).getString("phone_number");
                        String design = requestArray.getJSONObject(i).getString("design");
                        String is_done = requestArray.getJSONObject(i).getString("is_done");
                        String shop_name = requestArray.getJSONObject(i).getString("shop_name");
                        String shop_contact = requestArray.getJSONObject(i).getString("shop_contact");

                        requests.add(new CustomerPrintRequest(id,size,quantity,paper_quality,color_scheme,cost,phone_number,design,is_done,shop_name,shop_contact));
                    }

                    CartAdapter adapter = new CartAdapter(getContext(), requests);
                    list.setLayoutManager(new GridLayoutManager(getContext(),1));
                    list.setAdapter(adapter);


                }
                else {
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        });

        return v;
    }
}