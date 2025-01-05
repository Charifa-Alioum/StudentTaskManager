package com.example.studenttaskmanagerdetails;

import static android.content.Intent.getIntent;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studenttaskmanager.R;
import com.example.studenttaskmanagerfeatures.GradeCalculator;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.google.gson.Gson;

import java.util.Objects;


public class DetailsDialog extends DialogFragment {
    private String subjectName;
    private double caMark;
    private double snMark;
    private double tpMark;
    private double average;
    private String updateComment;
    private Button confirmDetailsButton;

    private TextView subjectNameTextView;
    private TextView caMarkTextView;
    private TextView snMarkTextView;
    private TextView tpMarkTextView;
    private TextView averageTextView;
    private EditText updateCommentEditText;

    private SubjectItem updatedSubject;

    private PreferenceManager preferenceManager;
    private Gson gson;

    private SubjectItem subjectItem;
    private int itemPosition;

    private OnDataChangedListener listener;
    public DetailsDialog() {
        // Required empty public constructor
    }

    public interface OnDataChangedListener{
        void onDataChanged(SubjectItem subject,int position);
    }

    public static DetailsDialog newInstance(SubjectItem subject,int position) {
        DetailsDialog fragment = new DetailsDialog();
        /*Bundle args=new Bundle();
        args.getString("subject_name",subject.getSubjectName());
        args.getBoolean("cc_checkbox",subject.isCcCheckbox());
        args.getDouble("cc_mark",subject.getCcMark());
        args.getBoolean("sn_checkbox",subject.isSnCheckbox());
        args.getDouble("sn_mark",subject.getSnMark());
        args.getBoolean("tp_checkbox",subject.isTpCheckbox());
        args.getDouble("tp_mark",subject.getTpMark());
        args.getString("comment",subject.getCommentZone());
        fragment.setArguments(args);*/

        fragment.subjectItem=subject;
        fragment.itemPosition=position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            subjectName=getArguments().getString("subjectname");
            caMark=getArguments().getDouble("ccmark");
            snMark=getArguments().getDouble("snmark");
            tpMark=getArguments().getDouble("tpmark");
            updateComment=preferenceManager.getString("subject_comment","here is the subject comment");
            updatedSubject= (SubjectItem) getArguments().getSerializable("update_subject");
            Log.d("DetailsDialog","Subject Name: "+subjectName);
        }
        else {
            Log.e("DetailsDialog","Null arguments");
        }
        if (getActivity()==null){
            Log.e("DetailsDialog","Fragment not attached to an activity");
            return null;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.details_dialog, null);
        if (view.getParent()!=null){
            ((ViewGroup) view.getParent()).removeView(view);
        }
        subjectNameTextView =view.findViewById(R.id.subject_name);
        subjectNameTextView.setText(subjectName);
        caMarkTextView=view.findViewById(R.id.subject_ca_mark);
        caMarkTextView.setText(Double.toString(caMark));
        snMarkTextView=view.findViewById(R.id.subject_final_mark);
        snMarkTextView.setText(Double.toString(snMark));
        tpMarkTextView=view.findViewById(R.id.subject_tps_mark);
        tpMarkTextView.setText(Double.toString(tpMark));
        averageTextView=view.findViewById(R.id.subject_average);
        updateCommentEditText=view.findViewById(R.id.comment_update);
        updateCommentEditText.setText(updateComment);
        confirmDetailsButton=view.findViewById(R.id.confirm_details_button);

        updateSubjectInfo(updatedSubject);

        builder.setView(view).setTitle("Subject Statistics")
                .setNegativeButton("Close",(dialog,id)->dismiss());
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnDataChangedListener){
            listener= (OnDataChangedListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement OnDataChangedListener");
        }
    }

    public void updateSubjectInfo(SubjectItem subject){
        subjectName=subject.getSubjectName();
        caMark=subject.getCcMark();
        snMark=subject.getSnMark();
        tpMark=subject.getTpMark();
        updateComment=subject.getCommentZone();
        average= GradeCalculator.calculateAverage(caMark,subject.isCcCheckbox(),snMark,
                subject.isSnCheckbox(),tpMark,subject.isTpCheckbox());

        if (subjectNameTextView!=null || caMarkTextView!=null || snMarkTextView!=null ||
        tpMarkTextView!=null || averageTextView!=null){
            subjectNameTextView.setText(subjectName);
            caMarkTextView.setText(Double.toString(caMark));
            tpMarkTextView.setText(Double.toString(tpMark));
            snMarkTextView.setText(Double.toString(snMark));
            updateCommentEditText.setText(updateComment);
            averageTextView.setText(Double.toString(average));
        }
        if (listener!=null){
            listener.onDataChanged(subject,itemPosition);
        }
    }
}