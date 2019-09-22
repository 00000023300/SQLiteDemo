package com.example.sqlitedemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.sqlitedemo.utils.MyDBHelper;

public class MyContentProvider extends ContentProvider {

    //    数据库及表相关常量
    private SQLiteDatabase db;
    private static final String TBL_NAME_STUDENT = "student";
    private static final String _ID = "_id";

//    ContentProvider相关常量
    private static final String CONTENT = "content://";
    private static final String AUTHORIY = "com.example.sqlitedemo";
    private static final String URI = CONTENT+AUTHORIY+"/"+TBL_NAME_STUDENT;

    public static final Uri STUDENT_URI =  Uri.parse(URI);

//    ContentProvider返回的数据类型的定义，数据集合
//数据集合
    private static final String STUDENT_TYPE="vnd.android.cursor.dir/vnd."+AUTHORIY;
    //    单项数据
    private static final String STUDENT_TYPE_ITEM="vnd.android.cursor.item/vnd."+AUTHORIY;
    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static final int STUDENTS = 1;
    static final int STUDENT_ITEM = 2;
    static {
        matcher.addURI(AUTHORIY,TBL_NAME_STUDENT,STUDENTS);
        matcher.addURI(AUTHORIY,TBL_NAME_STUDENT+"/#",STUDENT_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int newId = db.delete(TBL_NAME_STUDENT,selection,selectionArgs);
        if(newId>0){
            return newId;
        }
        throw new IllegalArgumentException("删除失败"+uri);
//       return null;
    }

//根据在UriMatch中存储的URI进行类型匹配
// 作用：在进行增删改查的操作时，根据type选择对应的数据表
    @Override
    public String getType(Uri uri) {
       switch (matcher.match(uri)){
           case STUDENTS:
         return STUDENT_TYPE;
      case STUDENT_ITEM:
          return STUDENT_TYPE_ITEM;
          default:
              throw new RuntimeException("错误的uri");
  }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
       long newId = 0;
       newId = db.insert(TBL_NAME_STUDENT,null,values);
       Uri newUri1 =  Uri.parse(CONTENT+AUTHORIY+"/"+TBL_NAME_STUDENT+"/"+newId);
       if(newId>0){
           return newUri1;
       }
        throw new IllegalArgumentException("插入失败"+uri);
//       return null;
    }

    @Override
    public boolean onCreate() {
        MyDBHelper helper = new MyDBHelper(getContext());
        db = helper.getWritableDatabase();

        return db!=null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
      Cursor cursor = null;
      switch (matcher.match(uri)){
          case STUDENTS:
              cursor = db.query(TBL_NAME_STUDENT,projection,selection,selectionArgs,null,null,sortOrder);
              break;
          case STUDENT_ITEM:
              String id = uri.getPathSegments().get(1);
              cursor = db.query(TBL_NAME_STUDENT,projection,"_id=?",new String[]{id},null,null,sortOrder);
              break;
      }
      return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
         int newId = db.update(TBL_NAME_STUDENT,values,selection,selectionArgs);
        if(newId>0){
            return newId;
        }
        throw new IllegalArgumentException("更新失败"+uri);
//       return null;
    }

}
