package com.example.appprojgui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanItemsActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int REQUEST_READ_MEDIA_IMAGES = 103;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        Button buttonStartScanning = findViewById(R.id.buttonStartScanning);
        Button buttonUploadFromGallery = findViewById(R.id.buttonUploadFromGallery);

        buttonStartScanning.setOnClickListener(v -> openCamera());
        buttonUploadFromGallery.setOnClickListener(v -> openGallery());

        findViewById(R.id.helpButton).setOnClickListener(this::showManualDialog);
    }

    // Method to show the manual dialog
    public void showManualDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.manual_dialog_layout, null);
        builder.setView(dialogView);

        TextView manual1 = dialogView.findViewById(R.id.manual1);
        manual1.setText(Html.fromHtml("<b>Home Page Overview</b><br/>" +
                "• Upon opening the app, users are greeted with a home page.<br/>" +
                "• The home page prominently features a main scanning button which prompts you to a different page and below it is a list of item categories, each represented by a button.<br/>" +
                "• Categories include Aseptic Cartons, Aerosol Cans, Cardboard, and more.<br/><br/>" +
                "<b>Navigation and Selection</b><br/>" +
                "• Users can explore different categories by tapping on the corresponding buttons of their choosing.<br/>" +
                "• Each category leads to detailed information about the recyclability of items belonging to that category.<br/>" +
                "• After the user is done reading, they can go back to the home page and select another category or click the “Scan Items” button to proceed to the item scanning."), TextView.BufferType.SPANNABLE);

        TextView manual2 = dialogView.findViewById(R.id.manual2);
        manual2.setText(Html.fromHtml(
                "<b>Instructions for Taking a Picture</b><br/>" +
                "• Navigate to the “Scan Items” section from the home page.<br/>" +
                "• Users are provided with instructions for capturing an image:<br/>" +
                "  o Ensure clear visibility of the item.<br/>" +
                "  o Ensure good lighting by taking pictures in well-lit areas.<br/>" +
                "  o Take pictures one at a time.<br/>" +
                "  o Use plain backgrounds or a background color similar to the item.<br/><br/>" +
                "<b>Start the Scanning Process</b><br/>" +
                "• Below the instructions, locate the “Start Scanning” button.<br/>" +
                "• Tap on the button and grant camera permissions to the app to start taking pictures.<br/>" +
                "• After granting permission, the interface of the user’s phone camera will open, allowing users to capture a picture of their chosen item.<br/><br/>" +
                "<b>Image Capture and Prediction Processing</b><br/>" +
                "• After taking a picture of an item, the app processes the image using image recognition.<br/>" +
                "• It predicts the type of the item captured based on the image content and compares it to the stored database information from the different categories. Take note that the prediction is not 100% accurate.<br/>"));

        TextView manual3 = dialogView.findViewById(R.id.manual3);
        manual3.setText(Html.fromHtml(
                "<b>Accessing the Gallery Upload Option</b><br/>" +
                "• In the \"Scan Items\" section, locate the \"Upload from Gallery\" button.<br/>" +
                "• Tap on \"Upload from Gallery\" to access the device's photo gallery.<br/><br/>" +
                "<b>Selecting and Uploading an Image</b><br/>" +
                "• Browse through the gallery and select the image of the item you want to scan.<br/>" +
                "• Tap on the selected image to upload it into the app for scanning.<br/><br/>" +
                "<b>Scanning Process with Uploaded Image</b><br/>" +
                "• After the user uploads an image, the app processes the image using image recognition.<br/>" +
                "• It predicts the type of the item captured based on the image content and compares it to the stored database information from the different categories. Take note that the prediction is not 100% accurate.<br/>"));

        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_READ_MEDIA_IMAGES);
            } else {
                openGalleryIntent();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
            } else {
                openGalleryIntent();
            }
        }
    }

    private void openGalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
        }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this, "com.example.appprojgui.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == GALLERY_REQUEST_CODE || requestCode == REQUEST_READ_MEDIA_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalleryIntent();
            } else {
                Toast.makeText(this, "Storage permission is required to access images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (photoURI != null) {
                    Intent intent = new Intent(this, DisplayImageActivity.class);
                    intent.putExtra("imageUri", photoURI.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Failed to capture image from camera", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    Intent intent = new Intent(this, DisplayImageActivity.class);
                    intent.putExtra("imageUri", imageUri.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No image data received from gallery", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }
}
