package com.example.appprojgui;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // If it's the first run, launch the onboarding activity
            Intent intent = new Intent(MainActivity.this, OnBoarding.class);
            startActivity(intent);
            finish(); // Finish the main activity
            return;
        }

        findViewById(R.id.buttonScanItems).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openscan_items();

            }
        });

        findViewById(R.id.buttonPaperWastes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaperWasteActivity();

            }
        });

        findViewById(R.id.buttonMetalCans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMetalCansActivity();

            }
        });

        findViewById(R.id.buttonPlasticBottles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlasticBottleActivity();

            }
        });

        findViewById(R.id.buttonGlassJars).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGlassJarActivity();

            }
        });

        findViewById(R.id.buttonAerosolCans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openAerosolCanActivity();

            }
        });

        findViewById(R.id.buttonAsepticCartons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openAsepticCartonActivity();

            }
        });

        findViewById(R.id.buttonBatteries).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openBatteryActivity();

            }

        });

        findViewById(R.id.buttonBrokenGlass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openBrokenGlassActivity();

            }
        });

        findViewById(R.id.buttonCardboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCardboardActivity();

            }
        });

        findViewById(R.id.buttonDrinkingGlass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openDrinkingGlassActivity();

            }
        });

        findViewById(R.id.buttonPlasticPackaging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openFlexiblePlasticPackagingActivity();

            }
        });

        findViewById(R.id.buttonGlassBottles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openGlassBottleActivity();

            }
        });

        findViewById(R.id.buttonLightBulbs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openLightBulbActivity();

            }
        });

        findViewById(R.id.buttonMugs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMugActivity();

            }
        });

        findViewById(R.id.buttonPlasticBags).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openPlasticBagActivity();

            }
        });

        findViewById(R.id.buttonPlasticJars).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openPlasticJarActivity();

            }
        });

        findViewById(R.id.buttonPlasticJugs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openPlasticJugActivity();

            }
        });

        findViewById(R.id.buttonPlasticUtensils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openPlasticUtensilActivity();

            }
        });

        findViewById(R.id.buttonStainedCardboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openStainedCardboardActivity();

            }
        });

        findViewById(R.id.buttonStyrofoam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openStyrofoamActivity();

            }
        });



    }

    public void openscan_items() {
        Intent intent = new Intent(this, ScanItemsActivity.class);
        startActivity(intent);
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

    public void openGlassJarActivity() {
        Intent intent = new Intent(this, GlassJarActivity.class);
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

    public void openLightBulbActivity() {
        Intent intent = new Intent(this, LightBulbActivity.class);
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

    public void openPlasticJarActivity() {
        Intent intent = new Intent(this, PlasticJarActivity.class);
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
