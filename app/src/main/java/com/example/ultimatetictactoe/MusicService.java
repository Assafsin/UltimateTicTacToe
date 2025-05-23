package com.example.ultimatetictactoe;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private static MediaPlayer player; //media player
    private ArrayList<Song> valuesList; //songs List
    private int songPosn;  //current position
    private int positionPausedInSong; // position in the pused song
    private final IBinder musicBind = new MusicBinder();
    private boolean isStopped; //state of the player
    @Override
    public void onCreate() {
        //create music Service

        super.onCreate();
        songPosn = 0;
        player = new MediaPlayer();
        valuesList = new ArrayList<Song>();

        //default music
        player = MediaPlayer.create(this, R.raw.default_music);
        player.setLooping(true);
        player.start();

        initMusicPlayer();

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

        getSongs();

    }

    public void getSongs() {
        //enter to list of songs from phone storege
        ContentResolver cr = getContentResolver();       //--allows access to the the phone
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;      //--songUri is the address to the music files in the phone
        Cursor songs = cr.query(songUri, null, null, null, null);
        if (songs != null && songs.moveToFirst()) {
            int songTitle = songs.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songID = songs.getColumnIndex(MediaStore.Audio.Media._ID);

            Song song;
            if (songs.moveToFirst()) {
                do {
                    long longSongID = songs.getLong(songID);
                    String currentTitle = songs.getString(songTitle);
                    song = new Song(longSongID, currentTitle);
                    valuesList.add(song);
                } while (songs.moveToNext());
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void initMusicPlayer() {
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        isStopped = false;
    }

    public void playSong() {
        // Ensure the list is not empty
        if (valuesList.isEmpty()) {
            Log.e("MusicService", "No songs found in the list!");
            return;
        }

        // Reset the player if a song is already playing
        if (player != null) {
            player.reset();
        }

        // Get the song to play from the list
        Song songToPlay = valuesList.get(songPosn);
        long songId = songToPlay.getId();

        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
        try {
            player.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();

        player.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        });
        isStopped = false;
    }


    public void setList(ArrayList<Song> theSongs) {
        valuesList = theSongs;
    }

    public class MusicBinder extends Binder implements IBinder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public void setSong(int songIndex) {
        songPosn = songIndex;
    }

    public void pause() {
        if (player != null)
            player.pause();
    }

    public boolean isPlaying() {
        // is music player playing. return true if it is else false
        return player.isPlaying();
    }

    public void resume() {
        if (player != null)
            player.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MusicService", "Service destroyed");

        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
        }
    }
}
