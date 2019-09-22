package com.example.sqlitedemo.entity;

import java.io.Serializable;

public class Student implements Serializable {

    public static final String TBL_Student= "create table if not exists student(" +
            "_id integer primary key autoincrement," +
            "name varchar(100) not null," +
            "age integer not null,"+
            "class_name varchar(100) not null)";


    private int id;
    private String name;
    private int age;
    private String class_name;

    public Student(){

    }

    public Student(int id, String name, int age, String class_name) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.class_name = class_name;
    }

    public static String getTBL_STUDENT() {
        return TBL_Student;
    }

    public int get_Id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}