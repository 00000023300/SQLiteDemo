package com.example.sqlitedemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.PatternMatcher;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.adapter.MyCursorAdapter;
import com.example.sqlitedemo.adapter.StudentAdapter;
import com.example.sqlitedemo.dao.StudentDAOImpl;
import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentService;
import com.example.sqlitedemo.entity.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnAdd, btnRevise, btnDelete,btnPeople;
    private ListView lvStudent;

    private static final int ADD_REQUEST = 100;
    private static final int MODIFY_REQUEST = 101;

    //private StudentAdapter studentAdapter;
    private MyCursorAdapter studentAdapter;
    private StudentDAOImpl dao;

    private List<Student> students;
    private List<String> cursors;
    private StudentService studentService;
    private int selectedPos;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new StudentDAOImpl(this);

        initData();
        initView();
    }

    private void initView() {
        btnAdd = findViewById(R.id.btn_add);
        btnRevise = findViewById(R.id.btn_revise);
        btnDelete = findViewById(R.id.btn_delete);
        btnPeople = findViewById(R.id.btn_people);
        lvStudent = findViewById(R.id.lv_student);

        //studentAdapter = new StudentAdapter(students);
        studentAdapter = new MyCursorAdapter(this,dao.selectByCursor());
        lvStudent.setAdapter(studentAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
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
                selectedStudent = new Student();
                selectedStudent.setId(cursor.getInt((cursor.getColumnIndex("_id"))));
                selectedStudent.setName(cursor.getString(cursor.getColumnIndex("name")));
                selectedStudent.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                selectedStudent.setClass_name(cursor.getString(cursor.getColumnIndex("class_name")));


                btnRevise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, InsertActivity.class);
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
//                        students.remove(position);
//                        studentAdapter.notifyDataSetChanged();
                        studentAdapter.changeCursor(dao.selectByCursor());
                    }
                });
            }
        });
        btnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        判断运行时权限
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},1);
                }else{
                    readContacts();

                }
            }
        });
    }
//1.获取联系人的cursor，组装联系人字符串放入list
    private void readContacts() {
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        cursors = new ArrayList<>();
        if(cursors!=null && cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                cursors.add(name + ":" + phone);
            }while(cursor.moveToNext());
            cursor.close();
        }
//        2.设置adapter
        if(cursors.isEmpty()){
            Toast.makeText(MainActivity.this,"没有联系人",Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R. layout.simple_list_item_1, cursors);
        lvStudent.setAdapter(arrayAdapter);

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }
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
//        if(data != null){
//            Bundle bundle=data.getExtras();
//            if(bundle == null){
//                return;
//            }
//            selectedStudent= (Student) bundle.get("student");
//            if(requestCode==MODIFY_REQUEST){
//                students.set(selectedPos,selectedStudent);
//            }else if(requestCode == ADD_REQUEST){
//                students.add(selectedStudent);
//            }
//            studentAdapter.notifyDataSetChanged();
//        }
        studentAdapter.changeCursor(dao.selectByCursor());
    }

}