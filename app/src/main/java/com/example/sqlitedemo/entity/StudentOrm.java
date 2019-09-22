package com.example.sqlitedemo.entity;



import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.*;

import static com.example.sqlitedemo.entity.Student.TBL_Student;

@DatabaseTable(tableName = "TBL_Student")
public class StudentOrm extends Student implements Serializable {
    @DatabaseField(generatedId = true)
    private int _id;

    @DatabaseField(index = true,columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField
    private String class_name;
    @DatabaseField(columnName = "age",dataType = DataType.INTEGER,canBeNull = true)
    private int age;

    public StudentOrm(){

    }

    public StudentOrm(int id, String name, int age, String class_name) {
        this._id = id;
        this.name = name;
        this.age = age;
        this.class_name = class_name;
    }

    public static String getTBL_STUDENT() {
        return TBL_Student;
    }

    public int get_Id() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + _id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}