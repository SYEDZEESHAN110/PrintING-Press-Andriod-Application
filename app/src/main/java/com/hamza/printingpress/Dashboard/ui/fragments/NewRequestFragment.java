package com.hamza.printingpress.Dashboard.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Adapders.RequestAdapter;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.ShopPrintRequest;
import com.hamza.printingpress.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class NewRequestFragment extends Fragment {
    Context context;
    ArrayList<ShopPrintRequest> data;
    RecyclerView list;
    public NewRequestFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_request, container, false);
        list = v.findViewById(R.id.request_list);
        data = new ArrayList<>();
        VolleyApiRequest.authArrayGet(context, URLs.shopNewRequests, null, e -> null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject request = response.getJSONObject(i);
                    data.add(ShopPrintRequest.convertJsonToThis(request));
                }

                RequestAdapter adapter = new RequestAdapter(getContext(), data);
                list.setLayoutManager(new GridLayoutManager(getContext(),1));
                list.setAdapter(adapter);
            }catch (Exception ignore) {}
            return null;
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}