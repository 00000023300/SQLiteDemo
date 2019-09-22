package com.example.sqlitedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;

import java.util.List;

public class StudentOrmAdapter extends BaseAdapter {
    private List<StudentOrm> students;
    private Context context;

    public StudentOrmAdapter(List<StudentOrm> students) {
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
            holder = new ViewHolder();

            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvClass = convertView.findViewById(R.id.tv_class);
            holder.tvAge = convertView.findViewById(R.id.tv_age);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentOrm student = students.get(position);
        holder.tvName.setText(student.getName());
        holder.tvClass.setText(String.valueOf(student.getClass_name()));
        holder.tvAge.setText(String.valueOf(student.getAge()));

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvClass;
        TextView tvAge;

    }
}