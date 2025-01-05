package com.example.studenttaskmanagerdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.studenttaskmanagerfeatures.GradeCalculator;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.google.gson.Gson;

import java.io.Serializable;

public class SubjectDetailsActivity extends AppCompatActivity implements DetailsDialog.OnDataChangedListener{
    private EditText nameInput;
    private View colorCircle;
    private Button saveButton;
    private CheckBox ccCheckbox,snCheckbox,tpCheckbox;
    private EditText subjectCCInput,subjectSNInput,subjectTPInput;
    private EditText commentText;

    private int currentColor= Color.BLUE;

    public static final String CURRENT_SUBJECT_NAME="currentSubject";

    private SubjectItem currentSubjectItem;

    private PreferenceManager preferenceManager;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject_details);
        preferenceManager=PreferenceManager.getInstance(this);
        gson=new Gson();

        nameInput=findViewById(R.id.subject_input);
        colorCircle=findViewById(R.id.color_circle);
        saveButton=findViewById(R.id.save_button);
        ccCheckbox=findViewById(R.id.cc_checkbox);
        snCheckbox=findViewById(R.id.sn_checkbox);
        tpCheckbox=findViewById(R.id.tp_checkbox);
        subjectCCInput=findViewById(R.id.subject_cc_input);
        subjectSNInput=findViewById(R.id.subject_sn_input);
        subjectTPInput=findViewById(R.id.subject_tp_input);
        commentText=findViewById(R.id.comments_input);

        TextView changeColor=findViewById(R.id.change_color);

        Intent intent=getIntent();

        //currentSubjectItem=(SubjectItem) intent.getSerializableExtra("subject_item");

        String currentName=intent.getStringExtra(CURRENT_SUBJECT_NAME);
        int color=intent.getIntExtra("color",Color.BLUE);
        boolean ccCheck=intent.getBooleanExtra("ccCheck",false);
        double ccMark=intent.getDoubleExtra("ccMark",0);
        boolean snCheck=intent.getBooleanExtra("snCheck",false);
        double snMark=intent.getDoubleExtra("snMark",0);
        boolean tpCheck=intent.getBooleanExtra("tpCheck",false);
        double tpMark=intent.getDoubleExtra("tpMark",0);
        String comment=intent.getStringExtra("comment");

        int position=intent.getIntExtra("element_position",-1);

        nameInput.setText(currentName);
        colorCircle.setBackgroundColor(color);
        ccCheckbox.setChecked(ccCheck);
        subjectCCInput.setText(Double.toString(ccMark));
        snCheckbox.setChecked(snCheck);
        subjectSNInput.setText(Double.toString(snMark));
        tpCheckbox.setChecked(tpCheck);
        subjectTPInput.setText(Double.toString(tpMark));
        commentText.setText(comment);

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
        Intent resultIntent=new Intent();

        String updatedName=nameInput.getText().toString();
        //currentSubjectItem.setSubjectName(updatedName);
        boolean ccCheck= ccCheckbox.isChecked();
        //currentSubjectItem.setCcCheckbox(ccCheck);
        double ccMark=Double.parseDouble(subjectCCInput.getText().toString());
        //currentSubjectItem.setCcMark(ccMark);
        boolean snCheck= snCheckbox.isChecked();
        //currentSubjectItem.setSnCheckbox(snCheck);
        double snMark=Double.parseDouble(subjectSNInput.getText().toString());
        //currentSubjectItem.setSnMark(snMark);
        boolean tpCheck= tpCheckbox.isChecked();
        //currentSubjectItem.setTpCheckbox(tpCheck);
        double tpMark=Double.parseDouble(subjectTPInput.getText().toString());
        //currentSubjectItem.setTpMark(tpMark);
        String comment=commentText.getText().toString();
        //currentSubjectItem.setCommentZone(comment);
        if(colorCircle.getBackground() instanceof ColorDrawable){
            ColorDrawable colorDrawable= (ColorDrawable) colorCircle.getBackground();
            int updatedColor=colorDrawable.getColor();
            resultIntent.putExtra("color",updatedColor);
            //currentSubjectItem.setColor(updatedColor);
        }

        resultIntent.putExtra(CURRENT_SUBJECT_NAME,updatedName);
        preferenceManager.putString(CURRENT_SUBJECT_NAME,updatedName);
        resultIntent.putExtra("ccCheck",ccCheck);
        preferenceManager.getBoolean("ccCheck",ccCheck);
        resultIntent.putExtra("ccMark",ccMark);
        preferenceManager.putDouble("ccMark",ccMark);
        resultIntent.putExtra("snCheck",snCheck);
        preferenceManager.putBoolean("snCheck",snCheck);
        resultIntent.putExtra("snMark",snMark);
        preferenceManager.putDouble("snMark",snMark);
        resultIntent.putExtra("tpCheck",tpCheck);
        preferenceManager.putBoolean("tpCheck",tpCheck);
        resultIntent.putExtra("tpMark",tpMark);
        preferenceManager.putDouble("tpMark",tpMark);
        resultIntent.putExtra("comment",comment);
        preferenceManager.putString("comment",comment);

        resultIntent.putExtra("element_position",position);
        resultIntent.putExtra("updated_subject_item", (Serializable) currentSubjectItem);
        Bundle bundle=new Bundle();
        bundle.putSerializable("update_subject", (Serializable) currentSubjectItem);

        Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private double calculateAverage(){
        double caMark=ccCheckbox.isChecked() ? Double.parseDouble(subjectCCInput.getText().toString()) : 0;
        double finalMark=snCheckbox.isChecked() ? Double.parseDouble(subjectSNInput.getText().toString()) : 0;
        double practicalMark=tpCheckbox.isChecked() ? Double.parseDouble(subjectTPInput.getText().toString()) : 0;

        try {
            double subjectAverage= GradeCalculator.calculateAverage(caMark,ccCheckbox.isChecked(),
                    finalMark,snCheckbox.isChecked(),practicalMark,tpCheckbox.isChecked());
            //il faut récuperer cette moyenne et l'afficher dans SubjectFragment
            return calculateAverage();
        }
        catch (IllegalArgumentException e){
            e.getMessage();
        }
        return 0.0;
    }

    @Override
    public void onDataChanged(SubjectItem subject,int position) {

    }
}