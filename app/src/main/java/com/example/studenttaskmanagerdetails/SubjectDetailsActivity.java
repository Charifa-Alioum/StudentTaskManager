package com.example.studenttaskmanagerdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

    public static final String CURRENT_SUBJECT_NAME="currentSubject";

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

        String currentName=intent.getStringExtra(CURRENT_SUBJECT_NAME);
        int position=intent.getIntExtra("element_position",-1);

        nameInput.setText(currentName);

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

        saveButton.setOnClickListener(v-> saveSubjectDetails(position));
    }

    private void changeCircleColor(){
        final String[] colors={"Red","Green","Yellow","Cyan","Magenta","Black","White"};
        final int[] colorValues={Color.RED,Color.GREEN,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.WHITE};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Pick a color")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentColor=colorValues[i];
                        colorCircle.setBackgroundColor(currentColor);
                    }
                });
        builder.create().show();
    }

    private void saveSubjectDetails(int position){
        String updatedName=nameInput.getText().toString();
        Intent resultIntent=new Intent();

        resultIntent.putExtra(CURRENT_SUBJECT_NAME,updatedName);
        resultIntent.putExtra("element_position",position);

        setResult(RESULT_OK,resultIntent);
        finish();
    }
}