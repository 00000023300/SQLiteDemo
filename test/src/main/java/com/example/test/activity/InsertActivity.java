package com.example.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.test.R;
import com.example.test.entity.Student;
import com.example.test.entity.StudentService;
import com.example.test.entity.StudentServiceImpl;

import java.util.Arrays;
import java.util.List;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etAge;
    private Spinner spClass;
    private Button btnDefine;
    private Button btnCancel;

    private List<String> class_name;
    private Student student;
    private StudentService studentService;
    private String flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        studentService = new StudentServiceImpl(this);

        initView();
        initData();


    }

    private void initData() {
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            student = (Student) bundle.getSerializable("student");
            if (student!=null){
                etName.setText(student.getName());
                etName.setEnabled(false);
                spClass.setSelection(class_name.indexOf(student.getClass_name()),true);
                etAge.setText(String.valueOf(student.getAge()));
            }
        }
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        spClass = findViewById(R.id.sp_class);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDefine = findViewById(R.id.btn_define);
        btnCancel.setOnClickListener(this);
        btnDefine.setOnClickListener(this);
        class_name= Arrays.asList(getResources().getStringArray(R.array.classmate));
        spClass.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,class_name));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_define:
                updataStudent();
                break;
            case R.id.btn_cancel:
                Intent intent = new Intent(InsertActivity.this, activityMainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void updataStudent() {
        if (student == null){
            student = new Student();
        }
        student.setName(etName.getText().toString());
        student.setClass_name((String) spClass.getSelectedItem());
        student.setAge(Integer.parseInt(etAge.getText().toString()));
        if("修改".equals(flag)) {
            studentService.modifyRealNumber(student);
        } else if("添加".equals(flag)) {
            studentService.insert(student);
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}