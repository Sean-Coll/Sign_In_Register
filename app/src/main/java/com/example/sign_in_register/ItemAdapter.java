package com.example.sign_in_register;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int layoutId;

    int cur_size,cur_fontstyle;
    String cur_theme;

    public ItemAdapter(Context context, int layoutId, List<Item> list) {
        super(context,layoutId,list);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences userTheme = getContext().getSharedPreferences("Theme", MODE_PRIVATE);
        cur_size = userTheme.getInt("FontSize",12);
        cur_fontstyle = userTheme.getInt("FontStyle",0);
        cur_theme = userTheme.getString("ColorString","#FFFFFFFF");

        Item item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);

        TextView item_text = (TextView) view.findViewById(R.id.item_text);
        TextView item_des = (TextView) view.findViewById(R.id.item_describtion);

        //  set theme
        item_text.setTypeface(null, cur_fontstyle);
        item_text.setText(item.getContentText());
        item_text.setTextSize(cur_size);
        item_text.setBackgroundColor(Color.parseColor(cur_theme));

        item_des.setTypeface(null, cur_fontstyle);
        item_des.setText(item.getextraParameter());
        item_des.setTextSize(cur_size);
        item_des.setBackgroundColor(Color.parseColor(cur_theme));
        return view;
    }

}