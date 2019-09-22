package com.example.sqlitedemo.entity;

import android.database.Cursor;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void insert(Student student);
    void modifyRealNumber(Student student);
    void delete(int _id);
    Cursor selectByCursor();
}