package com.hamza.printingpress.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.printingpress.Adapders.CategoryAdapter;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.Category;
import com.hamza.printingpress.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView list;
    CategoryAdapter adapter;
    ArrayList<Category> arraylist;
    Context context;
    ImageView flipperSlide;
    EditText search;

    int[] images = {R.drawable.testing, R.drawable.chat, R.drawable.printing};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        list=v.findViewById(R.id.categoryList);
        search=v.findViewById(R.id.search);
        ViewFlipper simpleViewFlipper = v.findViewById(R.id.viewFlipperId);

        arraylist = new ArrayList<>();

        VolleyApiRequest.arrayGet(context, URLs.servicesUrl, null, e -> null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    arraylist.add(new Category(obj.getInt("id"), obj.getString("image"), obj.getString("name")));

                } catch (JSONException ignore) {}
            }
            adapter = new CategoryAdapter(context, arraylist,requireActivity());
            list.setLayoutManager(new GridLayoutManager(context,2));
            list.setAdapter(adapter);
            return null;
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                adapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        for (int image : images) {
            flipperSlide = new ImageView(context);
            flipperSlide.setImageResource(image);
            simpleViewFlipper.addView(flipperSlide);
        }

        Animation out = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        Animation in = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        simpleViewFlipper.setFlipInterval(5000);
        // set auto start for flipping between views
        simpleViewFlipper.setAutoStart(true);
        simpleViewFlipper.startFlipping();

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}