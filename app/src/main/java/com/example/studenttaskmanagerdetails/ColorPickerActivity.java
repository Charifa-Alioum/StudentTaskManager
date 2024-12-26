package com.example.studenttaskmanagerdetails;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.studenttaskmanager.R;
import com.example.studenttaskmanager.SubjectFragment;

public class ColorPickerActivity extends AppCompatActivity {

    private EditText subjectText, teacherText;
    private TextView colorDisplay;
    private Button pickColorButton, saveButton;
    private int selectedColor= Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_color_picker);

        subjectText=findViewById(R.id.subjectName);
        teacherText=findViewById(R.id.teacherName);
        colorDisplay=findViewById(R.id.color_display);
        pickColorButton=findViewById(R.id.pick_color_button);
        saveButton=findViewById(R.id.save_button);

        pickColorButton.setOnClickListener(v->showColorPicker());
        saveButton.setOnClickListener(v->addSubjectToList());

    }

    private void showColorPicker(){
        final int[] colors={Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,
        Color.MAGENTA,Color.BLACK,Color.WHITE};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose a color")
                .setItems(new CharSequence[]{"Red","Green","Blue","Yellow","Cyan",
                "Magenta","Black","White"},((dialogInterface, i) -> {
                    selectedColor=colors[i];
                    colorDisplay.setBackgroundColor(selectedColor);
                }))
                .setNegativeButton("Cancel",((dialogInterface, i) ->
                        dialogInterface.dismiss()))
                .create()
                .show();
    }

    private void addSubjectToList(){
        String subject=subjectText.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("Subject",subject);
        setResult(RESULT_OK,intent);

        finish();
        Toast.makeText(this, "Matière enregistrée", Toast.LENGTH_SHORT).show();
    }



}