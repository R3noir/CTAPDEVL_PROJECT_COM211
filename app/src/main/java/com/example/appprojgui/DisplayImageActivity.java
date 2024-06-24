package com.example.appprojgui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appprojgui.ml.Model224x224;
//import com.example.appprojgui.ml.Quant90;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class DisplayImageActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    int ImageSize = 224;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        result = findViewById(R.id.Result);
        ImageView imageView = findViewById(R.id.imageView);

        Button TryAgain = findViewById(R.id.tryAgain);
        Button ReadMore = findViewById(R.id.readMore);
        ReadMore.setVisibility(View.VISIBLE);

        // Retrieve image data from intent
        if (getIntent().hasExtra("imageBitmap")) {
            Bitmap imageBitmap = getIntent().getParcelableExtra("imageBitmap");
            imageView.setImageBitmap(imageBitmap);
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, ImageSize, ImageSize, false);
            ClassifyImage(imageBitmap);

        } else if (getIntent().hasExtra("imageUri")) {
            Bitmap image = null;
            String imageUriString = getIntent().getStringExtra("imageUri");
            Uri imageUri = Uri.parse(imageUriString);
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageView.setImageURI(imageUri);
            image = Bitmap.createScaledBitmap(image, ImageSize, ImageSize, false);
            ClassifyImage(image);

        }

        if(result.getText().toString().equals("No Items Detected")){
            ReadMore.setVisibility(View.GONE);
        }

        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText = result.getText().toString();
                switch (resultText) {
                    case "Aeseptic Cartons":
                        openAsepticCartonActivity();
                        break;
                    case "Battery":
                        openBatteryActivity();
                        break;
                    case "Broken Glass":
                        openBrokenGlassActivity();
                        break;
                    case "Cardboard":
                        openCardboardActivity();
                        break;
                    case "Flexible Plastic Packaging":
                        openFlexiblePlasticPackagingActivity();
                        break;
                    case "Glass Bottles":
                        openGlassBottleActivity();
                        break;
                    case "Metal Cans":
                        openMetalCansActivity();
                        break;
                    case "Mugs":
                        openMugActivity();
                        break;
                    case "Paper Waste":
                        openPaperWasteActivity();
                        break;
                    case "Plastic Bags":
                        openPlasticBagActivity();
                        break;
                    case "Plastic Bottles":
                        openPlasticBottleActivity();
                        break;
                    case "Plastic Jugs":
                        openPlasticJugActivity();
                        break;
                    case "Plastic Utensils":
                        openPlasticUtensilActivity();
                        break;
                    case "Stained Cardboard":
                        openStainedCardboardActivity();
                        break;
                    case "Styrofoam":
                        openStyrofoamActivity();
                        break;
                    default:
                        // Handle the case where the result is not recognized
                        break;
                }
            }
        });
    }
    public void ClassifyImage(Bitmap image){
        try {
            Model224x224 model = Model224x224.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * ImageSize * ImageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            // inputFeature0.loadBuffer(byteBuffer);

            int[] intValues = new int[ImageSize * ImageSize];
            image.getPixels(intValues, 0,image.getWidth(),0,0, image.getWidth(), image.getHeight());

            int pixel =0;
            for(int i = 0; i < ImageSize; i++){
                for(int j=0; j < ImageSize; j++){
                    int val = intValues[pixel++];

                    byteBuffer.putFloat(((val >>16) & 0xFF) * (1.f/1));
                    byteBuffer.putFloat(((val >>8) & 0xFF) * (1.f/1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f/1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);
            // Runs model inference and gets result.
            Model224x224.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;

            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Aeseptic Cartons", "Battery", "Broken Glass", "Cardboard", "Flexible Plastic Packaging", "Glass Bottles", "Metal Cans", "Mugs", "Paper Waste", "Plastic Bags", "Plastic Bottles", "Plastic Jugs", "Plastic Utensils", "Stained Cardboard", "Styrofoam"};
            if(maxConfidence < 0.4) {
                result.setText("No Items Detected");
            } else {
                result.setText(classes[maxPos]);
            }
            Log.d(TAG, "ClassifyImage: " + classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            Log.e(TAG, "ClassifyImage: ", e);
            // TODO Handle the exception
        }

    }

    public void openPaperWasteActivity() {
        Intent intent = new Intent(this, PaperWasteActivity.class);
        startActivity(intent);
    }

    public void openMetalCansActivity() {
        Intent intent = new Intent(this, MetalCanActivity.class);
        startActivity(intent);
    }

    public void openPlasticBottleActivity() {
        Intent intent = new Intent(this, PlasticBottleActivity.class);
        startActivity(intent);
    }

    public void openAerosolCanActivity() {
        Intent intent = new Intent(this, AerosolCanActivity.class);
        startActivity(intent);
    }

    public void openAsepticCartonActivity() {
        Intent intent = new Intent(this, AsepticCartonActivity.class);
        startActivity(intent);
    }

    public void openBatteryActivity() {
        Intent intent = new Intent(this, BatteryActivity.class);
        startActivity(intent);
    }

    public void openBrokenGlassActivity() {
        Intent intent = new Intent(this, BrokenGlassActivity.class);
        startActivity(intent);
    }

    public void openCardboardActivity() {
        Intent intent = new Intent(this, CardboardActivity.class);
        startActivity(intent);
    }

    public void openDrinkingGlassActivity() {
        Intent intent = new Intent(this, DrinkingGlassActivity.class);
        startActivity(intent);
    }

    public void openFlexiblePlasticPackagingActivity() {
        Intent intent = new Intent(this, FlexiblePlasticPackagingActivity.class);
        startActivity(intent);
    }

    public void openGlassBottleActivity() {
        Intent intent = new Intent(this, GlassBottleActivity.class);
        startActivity(intent);
    }

    public void openMugActivity() {
        Intent intent = new Intent(this, MugActivity.class);
        startActivity(intent);
    }

    public void openPlasticBagActivity() {
        Intent intent = new Intent(this, PlasticBagActivity.class);
        startActivity(intent);
    }

    public void openPlasticJugActivity() {
        Intent intent = new Intent(this, PlasticJugActivity.class);
        startActivity(intent);
    }

    public void openPlasticUtensilActivity() {
        Intent intent = new Intent(this, PlasticUtensilActivity.class);
        startActivity(intent);
    }

    public void openStainedCardboardActivity() {
        Intent intent = new Intent(this, StainedCardboardActivity.class);
        startActivity(intent);
    }

    public void openStyrofoamActivity() {
        Intent intent = new Intent(this, StyrofoamActivity.class);
        startActivity(intent);
    }


}
