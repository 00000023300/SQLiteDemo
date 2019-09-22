package com.example.sqlitedemo.dao;

import android.database.Cursor;

import com.example.sqlitedemo.entity.Student;


import java.util.List;

public interface StudentDao {
    List<Student> selectAllStudents();
    void insert(Student student);
    void update(Student student);
    void delete(int _id);
//    void delete(String username);
    Cursor selectByCursor();
}
