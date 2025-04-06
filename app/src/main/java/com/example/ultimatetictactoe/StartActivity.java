package com.example.ultimatetictactoe;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    //toolbar
    androidx.appcompat.widget.Toolbar toolbar;

    Intent intent;

    Battery batteryReceiver;
    public static boolean isFirstTime = true;

    // for login / register players card
    private Dialog dialog;
    private EditText editdUsername, editDEmail, editDPassword;
    private Button btnDlogin,btnDRegister;
    private TextView tvUserName,tvDMessage;


    // **** SQLite database
    public static DatabaseHelper dbHelper;
    private Button btnLogin,btnRegister, btnMusic, btnExit, btnScoreList;

    //SharedPreferences save user name in this phone
    private SharedPreferences sharedPreferences;
    private String savedUsername;



    private boolean music = false;



// music player
// connect to class musiceservice

    public static MusicService musicService;

    private Intent playIntent;

    public static boolean isPlaying = false;

    // true if music is working else false
    private boolean musicBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        // **** SQLite database
        dbHelper = new DatabaseHelper(this);

        // Retrieve the username from SharedPreferences
        this.sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        this.savedUsername = sharedPreferences.getString("USERNAME", "DefaultUser");  // "DefaultUser" is a fallback value

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        if (this.isFirstTime) {
            batteryReceiver = new Battery();
            registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            this.isFirstTime=false;
        }
        else {
            // Unregister the receiver to stop it from receiving broadcasts
            unregisterReceiver(batteryReceiver);

        }


        tvUserName = (TextView) findViewById(R.id.tvUserName);
        if (this.savedUsername.equals("DefaultUser")) {
            tvUserName.setText("");
            this.btnLogin.setText("Login ");
        }
        else {
            tvUserName.setText(" Wellcome " + this.savedUsername + "! ");
            this.btnRegister.setText("Sign up");
        }

        btnMusic = (Button) findViewById(R.id.btnMusic);
        btnMusic.setOnClickListener(this);

        btnScoreList = (Button) findViewById(R.id.btnScoreList);
        btnScoreList.setOnClickListener(this);

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);


        musicService = new MusicService();
        musicBound = false;
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection,
                    Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
        musicService.pause();
    }

    //conect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            //get service
            musicService = binder.getService();
            // pass list
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicBound = false;
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == btnRegister.getId()) {
            createRegistrationDialog();
        }

        if (v.getId() == btnLogin.getId()) {
            createLoginDialog();
        }

        if (v.getId() == R.id.btnMusic)
        {
            intent = new Intent(StartActivity.this, MusicListActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.btnExit)
        {
            stopService(playIntent);
            musicService = null;
            finish();
        }

        if (v.getId() == btnScoreList.getId()) {
            startActivity(new Intent(this, LeaderBoard.class));
        }

        if (v.getId() == btnExit.getId()) {
            // This will finish the current activity and all activities in the task.
            finishAffinity();
        }
    }
    public void createLoginDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loginpopup);
        dialog.setTitle("Login");
        dialog.setCancelable(true);

        tvDMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvDMessage.setVisibility(View.INVISIBLE);

        editdUsername = (EditText) dialog.findViewById(R.id.editUsername);
        editDPassword = (EditText) dialog.findViewById(R.id.editPassword);

        btnDlogin = (Button) dialog.findViewById(R.id.btnLogin);
        btnDlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editdUsername.getText().toString().trim();

                String password = editDPassword.getText().toString().trim();


                // Validate fields
                if (username.isEmpty() ||password.isEmpty()) {
                    tvDMessage.setVisibility(View.VISIBLE);
                    tvDMessage.setText("Please fill all fields");

                    return;
                }


                boolean isRegistered = dbHelper.loginUser(username, password);
                tvDMessage.setVisibility(View.VISIBLE);
                if (isRegistered) {

                    tvDMessage.setText("Login successful");
                    // Save the username in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME", username);  // 'username' is a variable holding the user's name
                    editor.apply(); // or editor.commit();

                    intent = new Intent(StartActivity.this, AddPlayers.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);

                    // Optionally, navigate to LoginActivity here.
                } else {
                    tvDMessage.setText("Login failed please sign in first");
                }

            }
        });


        dialog.show();
    }

    public void createRegistrationDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.registratiorpopup);
        dialog.setTitle("Registration");
        dialog.setCancelable(true);


        tvDMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvDMessage.setVisibility(View.INVISIBLE);

        editdUsername = (EditText) dialog.findViewById(R.id.editUsername);
        editDPassword = (EditText) dialog.findViewById(R.id.editPassword);
        editDEmail = (EditText) dialog.findViewById(R.id.editEmail);

        btnDRegister = (Button) dialog.findViewById(R.id.btnRegister);
        btnDRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editdUsername.getText().toString().trim();
                String email = editDEmail.getText().toString().trim();
                String password = editDPassword.getText().toString().trim();


                // Validate fields
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    tvDMessage.setVisibility(View.VISIBLE);
                    tvDMessage.setText("Please fill all fields");

                    return;
                }

                // Validate email format (contains '@' and '.com')
                if (!email.contains("@") || !email.contains(".com")) {
                    tvDMessage.setVisibility(View.VISIBLE);
                    tvDMessage.setText("Invalid email format. Email must contain '@' and '.com'");
                    return;
                }

                boolean isRegistered = dbHelper.registerUser(username, email, password);
                tvDMessage.setVisibility(View.VISIBLE);
                if (isRegistered) {

                    tvDMessage.setText("Registration successful");

                    // Optionally, navigate to LoginActivity here.
                } else {
                    tvDMessage.setText("Registration failed user/main exist");


                }

            }
        });

        dialog.show();
    }

    private void unregisterBatteryReceiver() {
        if (isFirstTime) {
            try {
                unregisterReceiver(batteryReceiver);
            } catch (IllegalArgumentException e) {
                // Receiver might have been already unregistered.
                e.printStackTrace();
                isFirstTime = false;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-register if needed when the activity resumes
        unregisterBatteryReceiver();
    }

    @Override
    protected void onPause() {
        // Unregister to stop receiving broadcasts when not needed
        unregisterBatteryReceiver();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // As an extra precaution, ensure the receiver is unregistered
        unregisterBatteryReceiver();
        super.onDestroy();
    }

}