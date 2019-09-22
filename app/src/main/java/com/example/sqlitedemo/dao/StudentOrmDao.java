package com.example.sqlitedemo.dao;

import android.database.Cursor;

import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;

import java.util.List;

public interface StudentOrmDao {
    List<StudentOrm> selectAllStudents();

    void insert(StudentOrm student);

    void update(StudentOrm student);

    void delete(StudentOrm student);
}
