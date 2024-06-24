package com.example.appprojgui;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import android.content.SharedPreferences;
import java.util.ArrayList;
public class OnBoarding extends AppCompatActivity {
    // UI components
    ImageSlider imageSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Check if onboarding needs to be shown
        SharedPreferences sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);


        if (!isFirstRun) {
            // If not the first run, directly launch the main activity and finish onboarding
            launchMainActivity();
            return;
        }


        setContentView(R.layout.activity_onboarding);


        // Find the views from layout file
        imageSlider = findViewById(R.id.image_slider);
        Button getStartedButton = findViewById(R.id.get_started);


        // List of pictures in the slider
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.asset_1, null));
        imageList.add(new SlideModel(R.drawable.asset_2, null));
        imageList.add(new SlideModel(R.drawable.asset_3, null));
        imageList.add(new SlideModel(R.drawable.asset_4, null));
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);


        // Action listener for get started button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update SharedPreferences to indicate onboarding has been completed
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstRun", false);
                editor.apply();
                // Launch main activity
                launchMainActivity();
            }
        });
    }


    // Method to launch main activity
    private void launchMainActivity() {
        Intent intent = new Intent(OnBoarding.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the onboarding activity
    }
}


