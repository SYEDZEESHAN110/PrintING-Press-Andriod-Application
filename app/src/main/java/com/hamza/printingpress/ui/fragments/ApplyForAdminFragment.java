package com.hamza.printingpress.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ApplyForAdminFragment extends Fragment {
    TextInputLayout genderField;
    MaterialButton registerBtn;
    JSONObject data;
    TextInputEditText ownerName;
    TextInputEditText shopEmail;
    TextInputEditText shopPassword;
    TextInputEditText contactNumber;
    TextInputEditText shopName;
    TextInputEditText shopCity;
    TextInputEditText shopAddress;
    TextInputEditText shopLatitude;
    TextInputEditText shopLongitude;
    TextInputEditText easypaisaNumber;
    TextInputEditText jazzcashNumber;

    public ApplyForAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_apply_for_admin, container, false);

        genderField = v.findViewById(R.id.gender_field);
        String[] stringArrayGenders = new String[]{
                "Male",
                "Female",
                "Other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayGenders);
        AutoCompleteTextView sizeOfProductView = ((AutoCompleteTextView) Objects.requireNonNull(genderField.getEditText()));
        sizeOfProductView.setAdapter(adapter);

        registerBtn = v.findViewById(R.id.admin_register_btn);
        registerBtn.setOnClickListener(this::registerOwner);

        ownerName = v.findViewById(R.id.owner_name_field);
        shopEmail = v.findViewById(R.id.shop_email_field);
        shopPassword = v.findViewById(R.id.shop_password_field);
        contactNumber = v.findViewById(R.id.shop_phone_number);
        shopName = v.findViewById(R.id.shop_name_field);
        shopCity = v.findViewById(R.id.shop_city_field);
        shopAddress = v.findViewById(R.id.shop_address_field);
        shopLatitude = v.findViewById(R.id.shop_lat_field);
        shopLongitude = v.findViewById(R.id.shop_lon_field);
        easypaisaNumber = v.findViewById(R.id.shop_easypaisa_field);
        jazzcashNumber = v.findViewById(R.id.shop_jazzcash_field);

        return v;
    }

    private void registerOwner(View view) {
        if(this.validateDate()) {
            register();
        }
    }

    private boolean validateDate() {
        try {
            data = new JSONObject();
            String ownerName = Objects.requireNonNull(this.ownerName.getText()).toString();
            if (ownerName.isEmpty() || ownerName.length() < 2) {
                Toast.makeText(getContext(), "Owner name must be greater then 2 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("owner_name", ownerName);
            String shopEmail = Objects.requireNonNull(this.shopEmail.getText()).toString();
            if(shopEmail.isEmpty() || shopEmail.length() < 2) {
                Toast.makeText(getContext(), "Shop Email field is required and have at least 2 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_email", shopEmail);
            String shopPassword = Objects.requireNonNull(this.shopPassword.getText()).toString();
            if(shopPassword.isEmpty() || shopPassword.length() < 8) {
                Toast.makeText(getContext(), "Shop Password field is required and have at least 8 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_password", shopPassword);

            String ownerGender = Objects.requireNonNull(Objects.requireNonNull(this.genderField.getEditText()).getText()).toString();
            if(ownerGender.isEmpty()) {
                Toast.makeText(getContext(), "Owner Gender field is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("owner_gender", ownerGender);

            String contactNumber = Objects.requireNonNull(this.contactNumber.getText()).toString();
            if(contactNumber.length() != 10) {
                Toast.makeText(getContext(), "Contact Number field is required and have 10 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("contact_number", "+92" + contactNumber);
            String shopName = Objects.requireNonNull(this.shopName.getText()).toString();
            if(shopName.isEmpty() || shopName.length() < 2) {
                Toast.makeText(getContext(), "Shop Name field is required and have at least 2 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_name", shopName);
            String shopCity = Objects.requireNonNull(this.shopCity.getText()).toString();
            if(shopCity.isEmpty() || shopCity.length() < 2) {
                Toast.makeText(getContext(), "Shop City field is required and have at least 2 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_city", shopCity);
            String shopAddress = Objects.requireNonNull(this.shopAddress.getText()).toString();
            if(shopAddress.isEmpty() || shopAddress.length() < 2) {
                Toast.makeText(getContext(), "Shop Address field is required and have at least 2 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_address", shopAddress);
            String shopLatitude = Objects.requireNonNull(this.shopLatitude.getText()).toString();
            if(shopLatitude.isEmpty() ) {
                Toast.makeText(getContext(), "Shop Latitude field is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_lat", shopLatitude);
            String shopLongitude = Objects.requireNonNull(this.shopLongitude.getText()).toString();
            if(shopLongitude.isEmpty()) {
                Toast.makeText(getContext(), "Shop Longitude field is required.", Toast.LENGTH_SHORT).show();
                return false;
            }
            data.put("shop_lon", shopLongitude);
            String easypaisaNumber = Objects.requireNonNull(this.easypaisaNumber.getText()).toString();
            if(!easypaisaNumber.isEmpty() && easypaisaNumber.length() != 10) {
                Toast.makeText(getContext(), "Easypaisa Number field may have 10 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(easypaisaNumber.isEmpty()) {
                data.put("easypaisa_number", "");
            }else {
                data.put("easypaisa_number", "+92" +  easypaisaNumber);
            }
            String jazzcashNumber = Objects.requireNonNull(this.jazzcashNumber.getText()).toString();
            if(!jazzcashNumber.isEmpty() && jazzcashNumber.length() != 10) {
                Toast.makeText(getContext(), "Jazzcash Number field may have 10 characters", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(jazzcashNumber.isEmpty()) {
                data.put("jazzcash_number", "");
            }else {
                data.put("jazzcash_number", "+92" + jazzcashNumber);
            }

        }
        catch(Exception ignore) {
            return false;
        }
        return true;
    }

    private void register() {

        VolleyApiRequest.objectPost(getContext(), URLs.registerShopAdmin, data, e -> null, response -> {
            try {
                if(response.getBoolean("success")) {
                    Toast.makeText(getContext(), "Register successful", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.popBackStack();
                    navController.navigate(R.id.loginFragment);
                } else {
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        });

    }
}