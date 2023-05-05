package com.hamza.printingpress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

public class CanvasDesignActivity extends AppCompatActivity {
    private static final String file_type     = "image/*";    // file types to be allowed for upload
    private ValueCallback<Uri[]> file_path;     // received file(s) temp. location
    private final static int file_req_code = 10;

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_design);
        checkDownloadPermission();
        String url = "https://plc.techrepublica.com/design";

        WebView webview = findViewById(R.id.myWebView);
        //next line explained below
        webview.loadUrl(url);

        WebSettings webSettings = webview.getSettings();
        webview.setWebViewClient(new MyWebViewClient(this));
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);

        webSettings.setMixedContentMode(0);
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("console", consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if(checkDownloadPermission()) {
                    file_path = filePathCallback;

                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType(file_type);

                    Intent chooserIntent = Intent.createChooser(contentSelectionIntent, "Image chooser");
                    CanvasDesignActivity.this.startActivityForResult(chooserIntent, file_req_code);
                    return true;
                } else {
                    return false;
                }
            }
        });

        webview.setDownloadListener((url1, userAgent, contentDisposition, mimetype, contentLength) -> {
            String base64Image = url1.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            saveImage( decodedByte, "design.png", "image/png");
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Uri[] results = null;

        /*-- if file request cancelled; exited camera. we need to send null value to make future attempts workable --*/
        if (resultCode == Activity.RESULT_CANCELED) {
            file_path.onReceiveValue(null);
            return;
        }

        /*-- continue if response is positive --*/
        if(resultCode== Activity.RESULT_OK){
            if(null == file_path){
                return;
            }
            ClipData clipData;
            String stringData;

            try {
                clipData = intent.getClipData();
                stringData = intent.getDataString();
            }catch (Exception e){
                clipData = null;
                stringData = null;
            }
            if (clipData != null) { // checking if multiple files selected or not
                final int numSelectedFiles = clipData.getItemCount();
                results = new Uri[numSelectedFiles];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    results[i] = clipData.getItemAt(i).getUri();
                }
            } else if(stringData != null) {
                try {
                    Bitmap cam_photo = (Bitmap) intent.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    cam_photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    stringData = MediaStore.Images.Media.insertImage(this.getContentResolver(), cam_photo, null, null);
                }catch (Exception ignored){}
                results = new Uri[]{Uri.parse(stringData)};
            }

        }

        file_path.onReceiveValue(results);
        file_path = null;
    }

    public void saveImage(Bitmap b, String imageName, String imageType)
    {
        File downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File filePath = new File(downloadDirectory, imageName);
        if(filePath.exists()) {
            Date currentTime = Calendar.getInstance().getTime();
            filePath = new File(downloadDirectory, currentTime.getTime() + "-" + imageName);
        }

        FileOutputStream foStream;
        try
        {
            foStream = new FileOutputStream(filePath);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
            String imageFullPath = filePath.getAbsolutePath();
            Toast.makeText(CanvasDesignActivity.this, "Image save to: " + imageFullPath, Toast.LENGTH_SHORT).show();

            DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            manager.addCompletedDownload(imageName, "File downloaded successfully", true, imageType, filePath.getAbsolutePath(), filePath.length(), true);
        }
        catch (Exception e)
        {
            Toast.makeText(CanvasDesignActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }

    public boolean checkDownloadPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CanvasDesignActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }else{
            return true;
        }
    }

}

class MyWebViewClient extends WebViewClient {
    AppCompatActivity activity;
    public MyWebViewClient(AppCompatActivity activity) {
        super();
        this.activity = activity;
        //start anything you need to
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                this.activity.finish();
                return true;
            }

        }
        return super.shouldOverrideKeyEvent(view, event);
    }

}

