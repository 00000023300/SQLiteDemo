package com.example.test.dao;

import android.database.Cursor;


import com.example.test.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> selectAllStudents();
    void insert(Student student);
    void update(Student student);
    void delete(String studentName);
    Cursor selectByCursor();
}
