
package com.example.midexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final Activity activity;
    private final List<StudentModel> studentList;
    private final DBHelper dbHelper;

    public StudentAdapter(Activity activity, List<StudentModel> studentList,
                          DBHelper dbHelper) {
        this.activity = activity;
        this.studentList = studentList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item_list, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder,
                                 int position) {
        StudentModel student = studentList.get(position);

        holder.tvStudentName.setText(student.getStudentName());
        holder.tvStudentId.setText("Student ID: " + student.getStudentId());
        holder.tvEnrolled.setText(
                "Has Enrolled: " + (student.isEnrolled() ? "Yes" : "No"));

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("student_id", student.getStudentId());
            intent.putExtra("student_name", student.getStudentName());
            intent.putExtra("enrolled", student.isEnrolled());
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete "
                            + student.getStudentName() + "?")
                    .setNegativeButton("Cancel",
                            (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Delete", (dialog, which) -> {
                        boolean deleted = dbHelper.deleteStudent(
                                student.getStudentId());

                        if (deleted) {
                            int currentPosition =
                                    holder.getBindingAdapterPosition();

                            if (currentPosition != RecyclerView.NO_POSITION) {
                                studentList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                            }

                            Toast.makeText(activity, "Student deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Delete failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView tvStudentName, tvStudentId, tvEnrolled;
        Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvEnrolled = itemView.findViewById(R.id.tvEnrolled);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
