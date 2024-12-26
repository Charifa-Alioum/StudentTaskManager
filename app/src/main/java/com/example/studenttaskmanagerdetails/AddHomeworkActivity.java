package com.example.studenttaskmanagerdetails;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studenttaskmanager.HomeworkFragment;
import com.example.studenttaskmanager.R;

import java.util.Calendar;

public class AddHomeworkActivity extends AppCompatActivity {
    private EditText editTextName;
    private Button buttonAddHomework;
    private TextView textViewDate;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_homework);

        editTextName=findViewById(R.id.edit_text_name);
        buttonAddHomework=findViewById(R.id.button_add_homework);
        textViewDate=findViewById(R.id.text_view_date);

        selectedDate=Calendar.getInstance();

        textViewDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        selectedDate.set(Calendar.YEAR,year);
                        selectedDate.set(Calendar.MONTH,month);
                        selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        textViewDate.setText(String.format("%d/%d/%d",dayOfMonth,month+1,year));
                    },
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH),
                    selectedDate.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();

        });

        buttonAddHomework.setOnClickListener(v -> {
            String name=editTextName.getText().toString();
            HomeworkItem item=new HomeworkItem(name,false);
            HomeworkFragment fragment=(HomeworkFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if(fragment!=null){
                fragment.addHomeworkItem(item);
            }
            finish();
        });

    }
}