package com.hamza.printingpress.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hamza.printingpress.R;

public class ChatFragment extends Fragment {
    EditText msg;
    Button startChat;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        msg=v.findViewById(R.id.msg);
        startChat=v.findViewById(R.id.startChat);

        String number="+923142161472";

        startChat.setOnClickListener(v1 -> {
         boolean installed=isAppInstalled();

         if(installed){
             Intent intent=new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+number+"&text="+msg.getText()));
             startActivity(intent);
         } else {
             Toast.makeText(getContext(), "Whatsapp on this number: +923142161472", Toast.LENGTH_SHORT).show();
         }
            msg.setText("");

        });
        return v;
    }

    private boolean isAppInstalled() {
        PackageManager packageManager= requireActivity().getPackageManager();
        boolean isInstalled;

        try{
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            isInstalled=true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled=false;
            e.printStackTrace();
        }
        return isInstalled;
    }
}