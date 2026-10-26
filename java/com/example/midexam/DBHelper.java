package com.example.midexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student_db";
    private static final String TABLE_NAME = "student";

    private static final String COL_ID = "student_id";
    private static final String COL_NAME = "student_name";
    private static final String COL_ENROLLED = "enrolled";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " TEXT PRIMARY KEY, " +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_ENROLLED + " INTEGER NOT NULL)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID, student.getStudentId());
        values.put(COL_NAME, student.getStudentName());
        values.put(COL_ENROLLED,
                student.isEnrolled() ? 1 : 0);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public List<StudentModel> getStudentList() {
        List<StudentModel> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_NAME));
                boolean enrolled = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_ENROLLED)) == 1;

                students.add(
                        new StudentModel(id, name, enrolled));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return students;
    }

    public boolean updateStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, student.getStudentName());
        values.put(COL_ENROLLED,
                student.isEnrolled() ? 1 : 0);

        int result = db.update(
                TABLE_NAME,
                values,
                COL_ID + " = ?",
                new String[]{student.getStudentId()});

        return result > 0;
    }

    public boolean deleteStudent(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                COL_ID + " = ?",
                new String[]{studentId});

        return result > 0;
    }
}
