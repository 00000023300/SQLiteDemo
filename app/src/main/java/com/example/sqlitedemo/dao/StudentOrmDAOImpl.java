package com.example.sqlitedemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.sqlitedemo.entity.Student;
import com.example.sqlitedemo.entity.StudentOrm;
import com.example.sqlitedemo.utils.DBOrmHelper;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public class StudentOrmDAOImpl implements StudentOrmDao {

    private DBOrmHelper helper;
    private Dao<StudentOrm,Integer> dao;
    //private SQLiteDatabase dbs;
    public StudentOrmDAOImpl(Context context) {
        helper = DBOrmHelper.newInstance(context);
        try {
            dao = helper.getDao(StudentOrm.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StudentOrm> selectAllStudents() {
        List<StudentOrm> students = null;
        try {
            students = dao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void insert(StudentOrm student) {
        try {
            dao.create(student);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(StudentOrm student) {
        try {
            dao.update(student);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(StudentOrm student) {
        try {
            dao.delete(student);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}



