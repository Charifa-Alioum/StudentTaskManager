package com.example.studenttaskmanagerdetails;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studenttaskmanager.R;

public class SubjectDetailsActivity extends AppCompatActivity {
    private EditText nameInput;
    private View colorCircle;
    private Button saveButton;
    private CheckBox ccCheckbox,snCheckbox,tpCheckbox;
    private EditText subjectCCInput,subjectSNInput,subjectTPInput;

    private int currentColor= Color.BLUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject_details);

        nameInput=findViewById(R.id.subject_input);
        colorCircle=findViewById(R.id.color_circle);
        saveButton=findViewById(R.id.save_button);
        ccCheckbox=findViewById(R.id.cc_checkbox);
        snCheckbox=findViewById(R.id.sn_checkbox);
        tpCheckbox=findViewById(R.id.tp_checkbox);
        subjectCCInput=findViewById(R.id.subject_cc_input);
        subjectSNInput=findViewById(R.id.subject_sn_input);
        subjectTPInput=findViewById(R.id.subject_tp_input);

        TextView changeColor=findViewById(R.id.change_color);


        Intent intent=getIntent();
        String subjectName=intent.getStringExtra("subjectName");
        if(subjectName!=null){
            nameInput.setText(subjectName);
        }

        changeColor.setOnClickListener(v-> changeCircleColor());

        ccCheckbox.setOnCheckedChangeListener((buttonView,isChecked)->{
            subjectCCInput.setVisibility(isChecked ? View.VISIBLE: View.GONE);
        });

        snCheckbox.setOnCheckedChangeListener((buttonView,isChecked)->{
            subjectSNInput.setVisibility(isChecked ? View.VISIBLE: View.GONE);
        });

        tpCheckbox.setOnCheckedChangeListener((buttonView,isChecked)->{
            subjectTPInput.setVisibility(isChecked ? View.VISIBLE: View.GONE);
        });

        saveButton.setOnClickListener(v-> saveSubjectDetails());
    }

    private void changeCircleColor(){

    }

    private void saveSubjectDetails(){
        String updatedName=nameInput.getText().toString();
        Intent resultIntent=new Intent();

        resultIntent.putExtra("Subject",updatedName);

        setResult(RESULT_OK,resultIntent);
        finish();
    }
}