package com.example.ultimatetictactoe;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class AddPlayers extends AppCompatActivity implements View.OnClickListener {

    private EditText edPlayer1, edPlayer2;//
    private ImageView imgContact1, imgContact2, imgBack, imgStart;//
    private String firstname, name2;//

    private int playerNum;
    private Intent intent;
    private TextToSpeech textToSpeech; //
    //for content provider
    private ActivityResultLauncher<Intent> contentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_players);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        edPlayer1 = (EditText) findViewById(R.id.edPlayer1);
        edPlayer2 = (EditText) findViewById(R.id.edPlayer2);

        imgContact1 = (ImageView) findViewById(R.id.imgContact1);
        imgContact1.setOnClickListener(this);
        imgContact2 = (ImageView) findViewById(R.id.imgContact2);
        imgContact2.setOnClickListener(this);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        imgStart = (ImageView) findViewById(R.id.imgStart);
        imgStart.setOnClickListener(this);

        //get parms from previus activity
        Intent in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtraxs = in.getExtras();
            firstname = xtraxs.getString("USERNAME");
        }
        edPlayer1.setText(firstname);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        // for contact name
        initcontentP();
    }
    private void initcontentP() {

        contentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Cursor cursor = null;

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            try {
                                Uri uri = intent.getData();

                                cursor = getContentResolver().query(uri, null,
                                        null, null, null);
                                cursor.moveToFirst();


                                int phoneIndexName = cursor.getColumnIndex
                                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);


                                String phoneName = cursor.getString(phoneIndexName);
                              //  tvHead.setText(phoneName + " ");
                                if (playerNum == 1)
                                    edPlayer1.setText(phoneName);
                                else
                                    edPlayer2.setText(phoneName);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == imgContact1.getId()) {

            this.playerNum = 1;

            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK);
            contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

            contentLauncher.launch(contactPickerIntent);


        } else if (v.getId() == imgContact2.getId()) {
            this.playerNum = 2;
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK);
            contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

            contentLauncher.launch(contactPickerIntent);
        } else if (v.getId() == imgBack.getId()) {
            finish();
        } else if (v.getId() == imgStart.getId()) {
            firstname = edPlayer1.getText().toString().trim();
            name2 = edPlayer2.getText().toString().trim();
            if ((firstname.length() == 0 || name2.length() == 0) ||
                    firstname.equals(name2)) {
                new AlertDialog.Builder(this)
                        .setTitle(" Error!")
                        .setMessage("Users names must be added and different")
                        .setNeutralButton(" ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setIcon(R.drawable.error)
                        .show();

            } else {

                textToSpeech.speak(" Enjoy and Good Luck",
                        TextToSpeech.QUEUE_FLUSH, null);

                intent = new Intent(this, MainActivity.class);


                intent.putExtra("DATA1", firstname);
                intent.putExtra("DATA2", name2);

                startActivity(intent);


            }
        }
    }
}