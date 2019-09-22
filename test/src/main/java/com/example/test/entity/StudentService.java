package com.example.test.entity;

import android.database.Cursor;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void insert(Student student);
    void modifyRealNumber(Student student);
    void delete(String StudentName);
    Cursor selectByCursor();
}