package com.example.ultimatetictactoe;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadActivity extends AppCompatActivity implements SensorEventListener {

    //shake sensor
    private SensorManager sensorManager;
    //detect acceleration
    private Sensor accelerometer;

    // parameters to recognize a shake
    private static final float SHAKE_THRESHOLD = 1.0003431f;
    private static final int SHAKE_WAIT_TIME_MS = 500;

    private long lastShakeTime = 0;



    private final int TIME1=500;//להפןך את ה500 ל1 בשביל לקצר
    private final int TIME2=6000;//להפוך את ה6000 ל1 בשביל לקצר
    private ImageView t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }


        t1 = (ImageView) findViewById(R.id.img1);
        t2 = (ImageView) findViewById(R.id.img2);
        t3 = (ImageView) findViewById(R.id.img3);



        //animation of appearing and disappearing of the buttons, like in the game
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
            }
        }, 3*TIME1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t3.setVisibility(View.VISIBLE);
            }
        }, 4*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t2.setVisibility(View.VISIBLE);
            }
        }, 5*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.VISIBLE);
            }
        }, 6*TIME1);

        //another round of the animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
            }
        }, 7*TIME1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t3.setVisibility(View.VISIBLE);
            }
        }, 8*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t2.setVisibility(View.VISIBLE);
            }
        }, 9*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.VISIBLE);
            }
        }, 10*TIME1);


        // moving to the start activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),StartActivity.class);
                finish();//close this activity
                startActivity(i);
            }
        }, TIME2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener((SensorEventListener) this,
                    accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Normalize the acceleration values to get g-force
        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // Calculate the g-force
        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        // Debug output to Logcat
        android.util.Log.d("SHAKE", "gForce: " + gForce);

        if (gForce > SHAKE_THRESHOLD) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShakeTime > SHAKE_WAIT_TIME_MS) {
                lastShakeTime = currentTime;
                onShakeDetected();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not needed but you are still required to implement it
    }

    private void onShakeDetected() {
        Toast.makeText(this, "Shake detected!", Toast.LENGTH_SHORT).show();
        System.out.println("SHAKED");
        Intent i = new Intent(getApplicationContext(),StartActivity.class);
        finish();
        startActivity(i);
    }
}