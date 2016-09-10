package com.study.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private final String RES_PREFIX = "android.resource://com.study.miwok/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_activities);

        ArrayList<Word> words = new ArrayList<Word>();

        String[] englishNumbers = getResources()
                .getStringArray(R.array.english_words_numbers);
        String[] miwokNumbers = getResources()
                .getStringArray(R.array.miwok_words_numbers);

        int[] icons = new int[] {R.drawable.number_one, R.drawable.number_two,
                R.drawable.number_three, R.drawable.number_four, R.drawable.number_five,
                R.drawable.number_six, R.drawable.number_seven, R.drawable.number_eight,
                R.drawable.number_nine, R.drawable.number_ten};

        int[] audio = new int[] {R.raw.number_one, R.raw.number_two,
                R.raw.number_three, R.raw.number_four, R.raw.number_five,
                R.raw.number_six, R.raw.number_seven, R.raw.number_eight,
                R.raw.number_nine, R.raw.number_ten};

        for (int i = 0; i < englishNumbers.length; i++) {
            words.add(new Word(miwokNumbers[i], englishNumbers[i], icons[i], audio[i]));
        }

        ListView listView = (ListView) findViewById(R.id.list_root_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word item = (Word) adapterView.getItemAtPosition(i);
                final int audiId = item.getAudioId();

                Log.i(MainActivity.TAG, "onItemSelected in ColorsActivity, position:" +
                        " " + i);

                AudioManager.OnAudioFocusChangeListener listenerPlay = new AudioManager
                        .OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
//                        if( i == AudioManager.AUDIOFOCUS_LOSS){
//                            mediaPlayer.pause();
//                        }
                    }
                };

                AudioManager am = (AudioManager) getApplicationContext()
                        .getSystemService(AUDIO_SERVICE);
                int request = am.requestAudioFocus(listenerPlay, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);

                if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    try {
                        if(mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(getApplicationContext(), Uri.
                                parse(RES_PREFIX + audiId));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.reset();
                            }
                        });
                    }catch (IOException e){
                        Log.e(MainActivity.TAG, "IO Exception in loading an audio - URI");
                    }
                }

            }
        });

        WordAdapter adapter = new WordAdapter(this, R.layout.list_item, words);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
