package com.hamza.printingpress.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hamza.printingpress.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallFragment extends Fragment {
    CircleImageView callNow1,callNow2,callNow3,callNow4;

    public CallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_call, container, false);
        callNow1=v.findViewById(R.id.call1);
        callNow2=v.findViewById(R.id.call2);
        callNow3=v.findViewById(R.id.call3);

        callNow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"+923142161472"));//change the number
                startActivity(callIntent);
            }
        });

        callNow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"+923481507974"));//change the number
                startActivity(callIntent);
            }
        });

        callNow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"+923481507974"));//change the number
                startActivity(callIntent);
            }
        });


        return v;
    }
}