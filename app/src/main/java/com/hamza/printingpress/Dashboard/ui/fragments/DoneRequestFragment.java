package com.hamza.printingpress.Dashboard.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.printingpress.Adapders.RequestAdapter;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.ShopPrintRequest;
import com.hamza.printingpress.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class DoneRequestFragment extends Fragment {
    Context context;
    ArrayList<ShopPrintRequest> data;
    RecyclerView list;

    public DoneRequestFragment() {
        // Required empty public constructor
        data = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        data = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_done_request, container, false);
        list = v.findViewById(R.id.request_list);
        VolleyApiRequest.authArrayGet(context, URLs.shopDoneRequests, null, e -> null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject request = response.getJSONObject(i);
                    data.add(ShopPrintRequest.convertJsonToThisDone(request));
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