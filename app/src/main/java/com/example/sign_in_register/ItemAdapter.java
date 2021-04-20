package com.example.sign_in_register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int layoutId;

    public ItemAdapter(Context context, int layoutId, List<Item> list) {
        super(context,layoutId,list);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);

        TextView item_text = (TextView) view.findViewById(R.id.item_text);
        TextView item_des = (TextView) view.findViewById(R.id.item_describtion);


        item_text.setText(item.getContentText());
        item_des.setText(item.getextraParameter());

        return view;
    }

}