package com.study.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 08-09-2016.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private LayoutInflater inflater;

    public WordAdapter(Context context, int resource, ArrayList<Word> objects) {
        super(context, resource, objects);

        inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView != null)
            view = convertView;
        else
            view = inflater.inflate(R.layout.list_item, parent, false);
       // also possible LayoutInflater.from(getContext()).inflate(...);

        Word item = (Word) getItem(position);

        TextView textViewMiwok = (TextView) view.findViewById(R.id.miwok);
        textViewMiwok.setText(item.getMiwokWord());

        TextView textViewEnglish = (TextView) view.findViewById(R.id.english);
        textViewEnglish.setText(item.getEnglishWord());

        ImageView audioIcon = (ImageView) view.findViewById(R.id.icon_audio);
        audioIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        ImageView icon = (ImageView) view.findViewById(R.id.image_view);
        if(item.getIconId() > 0)
            icon.setImageResource(item.getIconId());
        else {
            ViewGroup vg = (ViewGroup)view;
            vg.removeView(icon);
        }


        return view;
    }
}
