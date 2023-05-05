package com.hamza.printingpress.ui.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hamza.printingpress.Api.URLs;
import com.hamza.printingpress.Api.VolleyApiRequest;
import com.hamza.printingpress.Models.ColorPrice;
import com.hamza.printingpress.Models.ShopAdmin;
import com.hamza.printingpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ServiceDetailFragment extends Fragment {
    String selectedService;
    int type = 0;
    private static final int PICK_PDF_FILE = 2;
    private TextView fileField;
    private Uri selectedFileUri;
    private final ArrayList<ShopAdmin> shops;
    TextInputLayout sizeOfProduct;
    TextInputLayout paperQuality;
    TextInputLayout colorScheme;
    TextInputLayout shopField;
    TextInputLayout paymentMethod;
    TextInputEditText quantityField;
    TextInputLayout phoneNumber;
    String[] ALLOWED_FILE_EXTENSIONS = new String[] {
        "jpg",
        "jpeg",
        "png",
        "pdf"
    };
    TextView paymentDetailText;
    ColorPrice colorPrices;
    String infoTextEasypaisa;
    String infoTextJazzCash;
    String infoTextBank;
    String infoText;

    public ServiceDetailFragment() {
        selectedService = "";
        selectedFileUri = null;
        this.colorPrices = null;
        this.infoTextEasypaisa = "";
        this.infoTextJazzCash = "";
        this.infoTextBank = "";
        this.infoText = "";


        shops = new ArrayList<>();
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PDF_FILE);
    }

    public void getAllShops(TextInputLayout shopField) {
        VolleyApiRequest.arrayGet(getContext(), URLs.allShops, null, e -> null, response -> {
            try {
                ArrayList<String> arr = new ArrayList<>();
                JSONObject shop;
                for (int i = 0; i < response.length(); i++) {
                    shop = response.getJSONObject(i);
                    arr.add(shop.getString("name"));
                    shops.add(ShopAdmin.extractThisFromJSON(shop));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, arr);
                AutoCompleteTextView shopFieldView = ((AutoCompleteTextView) Objects.requireNonNull(shopField.getEditText()));
                shopFieldView.setAdapter(adapter);
            } catch (Exception ignore) {
                Toast.makeText(getContext(), "Exception json", Toast.LENGTH_SHORT).show();
            }

            return null;
        });
    }

    private void getPriceCost() {
        VolleyApiRequest.objectGet(getContext(), URLs.colorPrices, null, e->null, response -> {
            try {
                colorPrices = new ColorPrice(response.getDouble("rgb"), response.getDouble("cmyk"), response.getDouble("grayscale"));
            } catch (Exception ignore) {
                Toast.makeText(getContext(), "Exception json", Toast.LENGTH_SHORT).show();
            }
            return null;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_service_detail, container, false);
        TextView serviceTitleView = v.findViewById(R.id.service_title);
        if (getArguments() != null) {
            selectedService = "Print a " + getArguments().getString("service");
            type = getArguments().getInt("type");

            serviceTitleView.setText(selectedService);
        }
        Button requestSaveBtn = v.findViewById(R.id.requestSaveBtn);
        quantityField = v.findViewById(R.id.quantityField);
        phoneNumber = v.findViewById(R.id.phoneNumber);
        paymentDetailText = v.findViewById(R.id.paymentDetailText);
        sizeOfProduct = v.findViewById(R.id.size_of_product);
        String[] stringArrayStatuses = new String[]{
                "13.34 x 20.96 cm",
                "15.24 x 22.86 cm",
                "15.88 x 23.50 cm",
                "20.32 x 25.40 cm",
                "20.96 x 26.04 cm",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayStatuses);
        AutoCompleteTextView sizeOfProductView = ((AutoCompleteTextView) Objects.requireNonNull(sizeOfProduct.getEditText()));
        sizeOfProductView.setAdapter(adapter);

        String[] stringArrayPaperQuality = new String[]{
                "50# White Smooth Offset",
                "60# White Smooth Offset",
                "60# Natural Smooth Offset",
                "70# White Smooth Offset",
                "80# White Gloss Text",
        };
        paperQuality = v.findViewById(R.id.paper_quality);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayPaperQuality);
        AutoCompleteTextView paperQualityView = ((AutoCompleteTextView) Objects.requireNonNull(paperQuality.getEditText()));
        paperQualityView.setAdapter(adapter2);

        String[] stringArrayColorScheme = new String[]{
                "rgb",
                "cmyk",
                "grayscale"
        };
        colorScheme = v.findViewById(R.id.color_scheme);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayColorScheme);
        AutoCompleteTextView colorSchemeView = ((AutoCompleteTextView) Objects.requireNonNull(colorScheme.getEditText()));
        colorSchemeView.setAdapter(adapter3);

        //Design file picker
        fileField = v.findViewById(R.id.design_file_field);
        fileField.setOnClickListener(view -> openFile());

        //Shop list
        shopField = v.findViewById(R.id.shop);
        getAllShops(shopField);
        getPriceCost();

        ArrayList<String> stringArrayPaymentMethods = new ArrayList<>();
        stringArrayPaymentMethods.add("Cash");
        paymentMethod = v.findViewById(R.id.payment_method);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayPaymentMethods);
        AutoCompleteTextView paymentMethodView = ((AutoCompleteTextView) Objects.requireNonNull(paymentMethod.getEditText()));
        paymentMethodView.setAdapter(adapter4);

        //Events
        Objects.requireNonNull(shopField.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                paymentMethodView.setText("");
                stringArrayPaymentMethods.clear();
                stringArrayPaymentMethods.add("Cash");
                for (ShopAdmin s:shops) {
                    if(s.getName().compareToIgnoreCase(charSequence.toString()) == 0) {
                        if (s.getEasypaisa_number().length() >= 10) {
                            stringArrayPaymentMethods.add("Easypaisa");
                            ServiceDetailFragment.this.infoTextEasypaisa = "Easypaisa Number: " + s.getEasypaisa_number();
                        }
                        if (s.getJazzcash_number().length() >= 10) {
                            stringArrayPaymentMethods.add("JazzCash");
                            ServiceDetailFragment.this.infoTextJazzCash = "JazzCash Number: " + s.getJazzcash_number();
                        }
                        if(s.getAccount_number().length() >= 2 && s.getAccount_title().length() >= 2) {
                            stringArrayPaymentMethods.add("Bank");
                            ServiceDetailFragment.this.infoTextBank = "Bank Account Title: " + s.getAccount_title() + ", Bank Account Number: " + s.getAccount_number() + ", Bank Name: " + s.getBank_name();
                        }
                        calculateAndShowPayment("");
                    }
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), R.layout.list_item, stringArrayPaymentMethods);
                AutoCompleteTextView paymentMethodView = ((AutoCompleteTextView) Objects.requireNonNull(paymentMethod.getEditText()));
                paymentMethodView.setAdapter(adapter4);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        requestSaveBtn.setOnClickListener(view -> {
            if(validateFields()) {
                saveRequest();
            }
        });

        quantityField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateAndShowPayment(ServiceDetailFragment.this.infoText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        colorScheme.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateAndShowPayment(ServiceDetailFragment.this.infoText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        paymentMethod.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (charSequence.toString()) {
                    case "Cash":
                        calculateAndShowPayment("Payment Mode: Cash On Pickup");
                        ServiceDetailFragment.this.infoText = "Payment Mode: Cash On Pickup";
                        break;
                    case "Easypaisa":
                        calculateAndShowPayment(ServiceDetailFragment.this.infoTextEasypaisa);
                        ServiceDetailFragment.this.infoText = ServiceDetailFragment.this.infoTextEasypaisa;
                        break;
                    case "JazzCash":
                        calculateAndShowPayment(ServiceDetailFragment.this.infoTextJazzCash);
                        ServiceDetailFragment.this.infoText = ServiceDetailFragment.this.infoTextJazzCash;
                        break;
                    case "Bank":
                        calculateAndShowPayment(ServiceDetailFragment.this.infoTextBank);
                        ServiceDetailFragment.this.infoText = ServiceDetailFragment.this.infoTextBank;
                        break;
                    default:
                        calculateAndShowPayment("");
                        ServiceDetailFragment.this.infoText = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    private boolean validateFields() {

        if(Objects.requireNonNull(this.quantityField.getText()).toString().length() <= 0) {
            Toast.makeText(getContext(), "Quantity must be greater then zero", Toast.LENGTH_SHORT).show();
            return false;
        }
        String sizeOfProduct = Objects.requireNonNull(this.sizeOfProduct.getEditText()).getText().toString();
        String paperQuality = Objects.requireNonNull(this.paperQuality.getEditText()).getText().toString();
        int quantity = Integer.parseInt(Objects.requireNonNull(this.quantityField.getText()).toString());
        String colorScheme = Objects.requireNonNull(this.colorScheme.getEditText()).getText().toString();
        String phoneNumber = Objects.requireNonNull(this.phoneNumber.getEditText()).getText().toString();
        String shop = Objects.requireNonNull(this.shopField.getEditText()).getText().toString();
        String paymentMethod = Objects.requireNonNull(this.paymentMethod.getEditText()).getText().toString();

        if(sizeOfProduct.isEmpty() || paperQuality.isEmpty() || quantity <= 0 || colorScheme.isEmpty() || phoneNumber.isEmpty() || shop.isEmpty() || paymentMethod.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedFileUri == null) {
            Toast.makeText(getContext(), "Please choose a file", Toast.LENGTH_SHORT).show();
            return false;
        }

        //File validation
        String fileMime = getMimeType(getContext(), selectedFileUri);
        boolean extensionMatch = Arrays.stream(ALLOWED_FILE_EXTENSIONS).anyMatch(item -> item.compareToIgnoreCase(fileMime) == 0);
        if(!extensionMatch) {
            Toast.makeText(getContext(), "Allow extensions are " + String.join(", ", ALLOWED_FILE_EXTENSIONS), Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            AssetFileDescriptor fileDescriptor = requireContext().getContentResolver().openAssetFileDescriptor(selectedFileUri, "r");
            long fileSize = fileDescriptor.getLength() / 1024;
            if(fileSize > 4096) {
                Toast.makeText(getContext(), "File size must be less then or equal to 4mb(4096kb)", Toast.LENGTH_LONG).show();
                return false;
            }

        }catch (Exception ignore) {
            Toast.makeText(getContext(), "File error occurred", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void saveRequest() {

        String sizeOfProduct = Objects.requireNonNull(this.sizeOfProduct.getEditText()).getText().toString();
        String paperQuality = Objects.requireNonNull(this.paperQuality.getEditText()).getText().toString();
        int quantity = Integer.parseInt(Objects.requireNonNull(this.quantityField.getText()).toString());
        String colorScheme = Objects.requireNonNull(this.colorScheme.getEditText()).getText().toString();
        String phoneNumber = Objects.requireNonNull(this.phoneNumber.getEditText()).getText().toString();
        String shop = Objects.requireNonNull(this.shopField.getEditText()).getText().toString();
        int shop_id = 0;
        for (ShopAdmin s:this.shops) {
            if(s.getName().compareToIgnoreCase(shop) == 0) {
                shop_id = s.getId();
            }
        }
        String paymentMethod = Objects.requireNonNull(this.paymentMethod.getEditText()).getText().toString();
        String fileString;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedFileUri);
            fileString = getStringImage(bitmap);
        } catch (IOException ignore) {
            Toast.makeText(getContext(), "File conversion error", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("service", this.selectedService);
            data.put("size", sizeOfProduct);
            data.put("paper_quality", paperQuality);
            data.put("quantity", quantity);
            data.put("color_scheme", colorScheme);
            data.put("phone_number", phoneNumber);
            data.put("shop_id", shop_id);
            data.put("payment_method", paymentMethod);
            data.put("design", fileString);

        } catch(Exception ignore){}

        VolleyApiRequest.objectPost(getContext(), URLs.savePrintRequest, data, e -> {
            Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
            return null;
        }, response -> {
            try {
                if(response.getBoolean("success")) {
                    Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                    int lastId = response.getInt("id");

                    SharedPreferences preferences = requireContext().getSharedPreferences("requests", Context.MODE_PRIVATE);
                    JSONArray savedRequests = new JSONArray(preferences.getString("orders", "[]"));
                    savedRequests.put(lastId);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("orders", savedRequests.toString());
                    editor.apply();
                    //Goto cart fragment
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.popBackStack();
                    navController.navigate(R.id.nav_cart);
                } else {
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        } );

    }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream streamBytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, streamBytes);
        byte[] imageBytes = streamBytes.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void calculateAndShowPayment(String infoText) {
        if(Objects.requireNonNull(this.quantityField.getText()).toString().length() <= 0) {
            paymentDetailText.setText("");
            return;
        }
        String paymentText = "";
        int quantity = Integer.parseInt(Objects.requireNonNull(this.quantityField.getText()).toString());
        String colorScheme = Objects.requireNonNull(this.colorScheme.getEditText()).getText().toString();
        if(quantity > 0 && colorScheme.length() >= 3) {
            double colorPrice = 300;
            if(this.colorPrices != null) {
                switch (colorScheme) {
                    case "rgb":
                        colorPrice = this.colorPrices.getRgb();
                        break;
                    case "cmyk":
                        colorPrice = this.colorPrices.getCmyk();
                        break;
                    case "grayscale":
                        colorPrice = this.colorPrices.getGrayscale();
                        break;
                }
            }
            paymentText += "Payment Amount: " + (colorPrice * quantity) + ", ";
        }
        paymentDetailText.setText(paymentText.concat(infoText));

    }

    private static String getMimeType(Context context, Uri uri) {
        String extension;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        return extension;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == PICK_PDF_FILE
                && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                selectedFileUri = uri;
                fileField.setText(uri.getPath());
            }
        }
    }
}