package com.example.sqlitedemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.adapter.StudentAdapter;
import com.example.sqlitedemo.dao.StudentOrmDAOImpl;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;
import com.example.sqlitedemo.entity.StudentService;
import com.example.sqlitedemo.entity.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainOrmActivity extends AppCompatActivity {
    private Button btnAdd, btnRevise, btnDelete;
    private ListView lvStudent;

    private static final int ADD_REQUEST = 100;
    private static final int MODIFY_REQUEST = 101;

    private StudentAdapter studentAdapter;
    //private MyCursorAdapter studentAdapter;
    private StudentOrmDAOImpl dao;

    private List<Student> students;
    private StudentService studentService;
    private int selectedPos;
    private StudentOrm selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new StudentOrmDAOImpl(this);

        initData();
        initView();
    }

    private void initView() {
        btnAdd = findViewById(R.id.btn_add);
        btnRevise = findViewById(R.id.btn_revise);
        btnDelete = findViewById(R.id.btn_delete);
        lvStudent = findViewById(R.id.lv_student);

        studentAdapter = new StudentAdapter(students);
        //studentAdapter = new MyCursorAdapter(this,dao.selectByCursor());
        lvStudent.setAdapter(studentAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainOrmActivity.this, InsertActivity.class);
                intent.putExtra("flag","添加");
                startActivityForResult(intent, ADD_REQUEST);

            }
        });

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                selectedPos = position;
                //selectedStudent = (Student) parent.getItemAtPosition(position);
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                selectedStudent = new StudentOrm();
                selectedStudent.setId(cursor.getInt((cursor.getColumnIndex("_id"))));
                selectedStudent.setName(cursor.getString(cursor.getColumnIndex("name")));
                selectedStudent.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                selectedStudent.setClass_name(cursor.getString(cursor.getColumnIndex("class_name")));


                btnRevise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainOrmActivity.this, InsertActivity.class);
                        intent.putExtra("flag","修改");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("student", selectedStudent);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, MODIFY_REQUEST);

                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentService.delete(selectedStudent.get_Id());
                        studentAdapter.notifyDataSetChanged();
                        
                    }
                });
            }
        });


    }

    private void initData() {
        studentService = new StudentServiceImpl(this);
        students = studentService.getAllStudents();
        if (students == null) {
            students = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(data != null){
            Bundle bundle=data.getExtras();
            if(bundle == null){
                return;
            }
            selectedStudent= (StudentOrm) bundle.get("student");
            if(requestCode==MODIFY_REQUEST){
                students.set(selectedPos,selectedStudent);
            }else if(requestCode == ADD_REQUEST){
                students.add(selectedStudent);
            }
            studentAdapter.notifyDataSetChanged();
        }
        //studentAdapter.changeCursor(dao.selectByCursor());
    }

}