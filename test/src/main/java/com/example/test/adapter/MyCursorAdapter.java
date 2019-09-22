package com.example.test.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;


public class MyCursorAdapter extends CursorAdapter {


    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student,viewGroup,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvAge = view.findViewById(R.id.tv_age);
        TextView tvClass = view.findViewById(R.id.tv_class);

        tvName.setText(cursor.getString(cursor.getColumnIndex("name")));
        tvAge.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("age"))));
        tvClass.setText(cursor.getString(cursor.getColumnIndex("class_name")));

    }
}
