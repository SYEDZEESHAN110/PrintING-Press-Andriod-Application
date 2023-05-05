package com.hamza.printingpress.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServicesFragment extends Fragment {
    NavController navController;

    Context context;
    GridView list;
    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_services, container, false);
        list = v.findViewById(R.id.serviceGrid);

        ArrayList<String> array = new ArrayList<>();
        VolleyApiRequest.arrayGet(context, URLs.servicesUrl, null, e -> null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    array.add(obj.getString("name"));

                } catch (JSONException ignore) {}
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    R.layout.single_service_item, R.id.ser_name, array);
            list.setAdapter(adapter);

            return null;
        });



        list.setOnItemClickListener((parent, view, position, id) -> {
            navController= Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            Bundle bundle = new Bundle();
            bundle.putString("service",array.get(position));
            bundle.putInt("type",1);
            navController.navigate(R.id.serviceDetailFragment,bundle);
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}