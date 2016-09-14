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
 * Created by lenovo on 11-09-2016.
 */
public class ColorsFragment extends Fragment {

    private final String RES_PREFIX = "android.resource://com.study.miwok/";
    private MediaPlayer mediaPlayer;
    private AudioManager am;
    private AudioManager.OnAudioFocusChangeListener listenerPlay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.words_activities, container, false);
        ArrayList<Word> words = new ArrayList<Word>();

        String[] englishWords = getResources()
                .getStringArray(R.array.english_words_colors);
        String[] miwokWords = getResources()
                .getStringArray(R.array.miwok_words_colors);

        int[] icons = new int[]{R.drawable.color_red, R.drawable.color_green,
                R.drawable.color_brown, R.drawable.color_gray, R.drawable.color_black,
                R.drawable.color_white, R.drawable.color_dusty_yellow,
                R.drawable.color_mustard_yellow};

        int[] audio = new int[]{R.raw.color_red, R.raw.color_green,
                R.raw.color_brown, R.raw.color_gray, R.raw.color_black,
                R.raw.color_white, R.raw.color_dusty_yellow,
                R.raw.color_mustard_yellow};

        for (int i = 0; i < englishWords.length; i++) {
            words.add(new Word(miwokWords[i], englishWords[i], icons[i], audio[i]));
        }

        final ListView listView = (ListView) view.findViewById(R.id.list_root_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                am.abandonAudioFocus(listenerPlay);

                Word item = (Word) adapterView.getItemAtPosition(i);
                final int audiId = item.getAudioId();

                Log.i(MainActivity.TAG, "onItemSelected in ColorsActivity, position:" +
                        " " + i);

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
                                // reset
                                mediaPlayer.reset();
                                // unregister the onFocusChange listener
                                am.abandonAudioFocus(listenerPlay);
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

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        listenerPlay = new AudioManager
                .OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int i) {
                if (i == AudioManager.AUDIOFOCUS_LOSS) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        am = null;
    }

}
