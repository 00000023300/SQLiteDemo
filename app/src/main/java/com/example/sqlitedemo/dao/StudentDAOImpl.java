package com.example.sqlitedemo.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.provider.MyContentProvider;
import com.example.sqlitedemo.utils.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDao {

    private MyDBHelper helper;
    private SQLiteDatabase dbs;
    private Context context;

    public StudentDAOImpl(Context context){
        //调用MyDBHelper类的构造方法时
        //若发现demo.db不存在会调用onCreate创建
        //若发现demo.db存在，且version的版本与已有的不一致，则调用onUpgrade方法更新
        helper=new MyDBHelper(context);
        this.context = context;

    }

    @Override
    public List<Student> selectAllStudents() {
        String sql = "select * from student";
        List<Student> students = null;


        // 1. 获取SQLiteDatabase对象
        dbs = helper.getReadableDatabase();

        // 2. 执行SQL查询
//        Cursor cursor = dbs.rawQuery(sql, null);
        Cursor cursor = context.getContentResolver().query(MyContentProvider.STUDENT_URI,null,
                null,null,null);

        // 3. 处理结果
        if (cursor != null && cursor.getCount() > 0) {
            students = new ArrayList<>();
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                student.setClass_name(cursor.getString(cursor.getColumnIndex("class_name")));
                students.add(student);
            }
            // 4. 关闭cursor
            cursor.close();
        }
        // 5. 返回结果
        return students;
    }


    //给CursorAdapter适配器使用
    public Cursor selectByCursor(){
        dbs = helper.getReadableDatabase();
        return dbs.query("Student",null,null,null,null,null,null);
    }




    @Override
    public void insert(Student student) {
        dbs = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("age",student.getAge());
        values.put("class_name",student.getClass_name());
//        context.getContentResolver().insert(MyContentProvider.STUDENT_URI, values);
//   通过ContentProvider插入数据
        Uri uri = context.getContentResolver().insert(MyContentProvider.STUDENT_URI,values);
        Log.i("SQLiteDemo",uri!=null ? uri.toString():"");

//        String sql = "insert into student values(null,?,?,?)";
//        dbs.execSQL(sql, new Object[]{
//                student.getName(),
//                student.getClass_name(),
//                student.getAge()});
        dbs.close();

    }

    @Override
    public void update(Student student) {
        // 1. 获取dbs对象
        dbs = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("age",student.getAge());
        values.put("class_name",student.getClass_name());

        context.getContentResolver().update(MyContentProvider.STUDENT_URI,
                values,
                "_id=?",
                new String[]{String.valueOf(student.get_Id())});
        dbs.close();
        // 2. 执行sql
//        String sql = "update student set student_id=? where name=?";
//        dbs.execSQL(sql, new Object[]{
//                student.getName(),
//                student.getClass_name(),
//        });

    }
    @Override
    public void delete(int _id) {
        // 1. 获取db对象
        dbs = helper.getWritableDatabase();
        // 2. 执行sql
//        String sql = "delete from student where name=?";
//        dbs.execSQL(sql, new Object[]{ studentName });

        dbs.close();

        context.getContentResolver().delete(MyContentProvider.STUDENT_URI,
                "_id=?",
                new String[]{String.valueOf(_id)});

    }
}