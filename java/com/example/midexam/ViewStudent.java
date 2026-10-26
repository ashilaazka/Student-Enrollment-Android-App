
package com.example.midexam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewStudent extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView rvStudents;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        dbHelper = new DBHelper(this);
        rvStudents = findViewById(R.id.rvStudents);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));

        loadStudents();
    }

    private void loadStudents() {
        List<StudentModel> students = dbHelper.getStudentList();

        studentAdapter = new StudentAdapter(
                this, students, dbHelper);

        rvStudents.setAdapter(studentAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dbHelper != null && rvStudents != null) {
            loadStudents();
        }
    }
}
