
package com.example.midexam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etStudentName, etStudentId;
    private CheckBox cbEnrolled;
    private Button btnAdd, btnView;
    private DBHelper dbHelper;

    private String originalStudentId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStudentName = findViewById(R.id.etStudentName);
        etStudentId = findViewById(R.id.etStudentId);
        cbEnrolled = findViewById(R.id.cbEnrolled);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        dbHelper = new DBHelper(this);

        // Check whether this page is opened for editing
        if (getIntent().hasExtra("student_id")) {
            originalStudentId = getIntent().getStringExtra("student_id");

            etStudentId.setText(originalStudentId);
            etStudentId.setEnabled(false);

            etStudentName.setText(
                    getIntent().getStringExtra("student_name"));

            cbEnrolled.setChecked(
                    getIntent().getBooleanExtra("enrolled", false));

            btnAdd.setText("UPDATE STUDENT");
        }

        btnAdd.setOnClickListener(v -> saveStudent());

        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    ViewStudent.class);
            startActivity(intent);
        });
    }

    private void saveStudent() {
        String name = etStudentName.getText().toString().trim();
        String id = etStudentId.getText().toString().trim();
        boolean enrolled = cbEnrolled.isChecked();

        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this,
                    "Please enter Student Name and Student ID",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StudentModel student = new StudentModel(id, name, enrolled);
        boolean success;

        if (originalStudentId == null) {
            success = dbHelper.addStudent(student);
        } else {
            success = dbHelper.updateStudent(student);
        }

        if (success) {
            Toast.makeText(this,
                    originalStudentId == null
                            ? "Student added successfully"
                            : "Student updated successfully",
                    Toast.LENGTH_SHORT).show();

            if (originalStudentId != null) {
                finish();
            } else {
                etStudentName.setText("");
                etStudentId.setText("");
                cbEnrolled.setChecked(false);
                etStudentName.requestFocus();
            }
        } else {
            Toast.makeText(this,
                    originalStudentId == null
                            ? "Add failed. Student ID may already exist."
                            : "Update failed",
                    Toast.LENGTH_LONG).show();
        }
    }
}
