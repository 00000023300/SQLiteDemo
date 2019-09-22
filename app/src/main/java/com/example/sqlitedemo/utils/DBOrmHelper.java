package com.example.sqlitedemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sqlitedemo.entity.StudentOrm;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBOrmHelper extends OrmLiteSqliteOpenHelper {
    public DBOrmHelper(Context context) {
        super(context, "student.db", null,1);
    }
    private static DBOrmHelper ormDBHelpter;
    public static synchronized DBOrmHelper newInstance(Context context){
        if (ormDBHelpter == null){
            ormDBHelpter = new DBOrmHelper(context);
        }
        return ormDBHelpter;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, StudentOrm.class);
            Log.i("DBOrmHelper","student表创建成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,StudentOrm.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
