package com.example.ultimatetictactoe;

import static com.example.ultimatetictactoe.StartActivity.musicService;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MusicListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION_CODE = 1;

    private Switch switchMusic;
    private ImageView back;
    private ListView lvSongs;

    private ArrayList<Song> songList;
    private ArrayList<String> songsNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_list);

        songsNames = new ArrayList<>();
        songList = new ArrayList<>();
        lvSongs = findViewById(R.id.lvSongs);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsNames);
        lvSongs.setAdapter(adapter);

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (musicService != null) {
                    musicService.setSong(i);
                    musicService.playSong();
                } else {
                    Toast.makeText(MusicListActivity.this, "Music service not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchMusic = findViewById(R.id.switchMusic);
        switchMusic.setOnClickListener(this);

        if (musicService.isPlaying()) switchMusic.setChecked(true);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses READ_MEDIA_AUDIO
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                        REQUEST_PERMISSION_CODE);
            } else {
                getSongs();
            }
        } else {
            // Pre-Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_CODE);
            } else {
                getSongs();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSongs();
            } else {
                Toast.makeText(this, "Permission denied to read media", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getSongs() {
        ContentResolver cr = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songs = cr.query(songUri, null, null, null, null);

        if (songs != null && songs.moveToFirst()) {
            int titleColumn = songs.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = songs.getColumnIndex(MediaStore.Audio.Media._ID);
                do {
                    String currentTitle = songs.getString(titleColumn);
                    long currentId = songs.getLong(idColumn);

                    songsNames.add(currentTitle);
                    songList.add(new Song(currentId, currentTitle));
                } while (songs.moveToNext());

                songs.close();
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (StartActivity.isPlaying && musicService != null) {
            musicService.resume();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }

        if (view.getId() == R.id.switchMusic) {
            if (switchMusic.isChecked()) {
                if (musicService != null) musicService.resume();
                StartActivity.isPlaying = true;
            } else {
                if (musicService != null) musicService.pause();
                StartActivity.isPlaying = false;
            }
        }
    }
}
