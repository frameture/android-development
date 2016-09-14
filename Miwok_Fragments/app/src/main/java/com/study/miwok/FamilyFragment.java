package com.study.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private final String RES_PREFIX = "android.resource://com.study.miwok/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.words_activities, container, false);
        ArrayList<Word> words = new ArrayList<Word>();

        String[] englishNumbers = getResources()
                .getStringArray(R.array.english_words_family);
        String[] miwokNumbers = getResources()
                .getStringArray(R.array.miwok_words_family);

        int[] icons = new int[]{R.drawable.family_father,
                R.drawable.family_mother, R.drawable.family_son,
                R.drawable.family_daughter, R.drawable.family_older_brother,
                R.drawable.family_younger_brother, R.drawable.family_older_sister,
                R.drawable.family_younger_sister, R.drawable.family_grandmother,
                R.drawable.family_grandfather};

        int[] audio = new int[]{R.raw.family_father,
                R.raw.family_mother, R.raw.family_son,
                R.raw.family_daughter, R.raw.family_older_brother,
                R.raw.family_younger_brother, R.raw.family_older_sister,
                R.raw.family_younger_sister, R.raw.family_grandmother,
                R.raw.family_grandfather};

        for (int i = 0; i < englishNumbers.length; i++) {
            words.add(new Word(miwokNumbers[i], englishNumbers[i], icons[i], audio[i]));
        }

        ListView listView = (ListView) view.findViewById(R.id.list_root_view);
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

                AudioManager am = (AudioManager) getActivity()
                        .getSystemService(Context.AUDIO_SERVICE);
                int request = am.requestAudioFocus(listenerPlay, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);

                if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    try {
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(getActivity(), Uri.
                                parse(RES_PREFIX + audiId));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.reset();
                            }
                        });
                    } catch (IOException e) {
                        Log.e(MainActivity.TAG, "IO Exception in loading an audio - URI");
                    }
                }

            }
        });

        WordAdapter adapter = new WordAdapter(getActivity(), R.layout.list_item, words);
        listView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }


}
