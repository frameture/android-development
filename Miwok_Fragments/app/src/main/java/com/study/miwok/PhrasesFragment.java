package com.study.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by lenovo on 12-09-2016.
 */
public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private final String RES_PREFIX = "android.resource://com.study.miwok/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.words_activities, container, false);

        ArrayList<Word> words = new ArrayList<Word>();

        String[] englishNumbers = getResources()
                .getStringArray(R.array.english_words_phrases);
        String[] miwokNumbers = getResources()
                .getStringArray(R.array.miwok_words_phrases);

        int[] audio = new int[]{R.raw.phrase_where_are_you_going, R.raw.phrase_what_is_your_name,
                                R.raw.phrase_my_name_is, R.raw.phrase_how_are_you_feeling,
                                R.raw.phrase_im_feeling_good, R.raw.phrase_are_you_coming,
                                R.raw.phrase_yes_im_coming, R.raw.phrase_im_coming,
                                R.raw.phrase_lets_go, R.raw.phrase_come_here};

        for (int i = 0; i < englishNumbers.length; i++) {
            words.add(new Word(miwokNumbers[i], englishNumbers[i], audio[i]));
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

                if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    try {
                        if(mediaPlayer.isPlaying())
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
                    }catch (IOException e){
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
