package com.example.studenttaskmanagerdetails;

import static android.content.Intent.getIntent;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenttaskmanager.R;


public class DetailsDialog extends DialogFragment {
    private String subjectName;
    private double caMark;
    private double snMark;
    private double tpMark;
    private double average;
    private String updateComment;
    private Button confirmDetailsButton;

    public DetailsDialog() {
        // Required empty public constructor
    }


    public static DetailsDialog newInstance(String param1, String param2) {
        DetailsDialog fragment = new DetailsDialog();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments()!=null){
            subjectName=getArguments().getString("subject_name");
            caMark=getArguments().getDouble("ca_mark");
            snMark=getArguments().getDouble("sn_mark");
            tpMark=getArguments().getDouble("tp_mark");
            updateComment=getArguments().getString("comment");
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.details_dialog, null);
        if (view.getParent()!=null){
            ((ViewGroup) view.getParent()).removeView(view);
        }
        TextView subjectNameTextView =view.findViewById(R.id.subject_name);
        subjectNameTextView.setText(subjectName);
        TextView caMarkTextView=view.findViewById(R.id.subject_ca_mark);
        caMarkTextView.setText(Double.toString(caMark));
        TextView snMarkTextView=view.findViewById(R.id.subject_final_mark);
        snMarkTextView.setText(Double.toString(snMark));
        TextView tpMarkTextView=view.findViewById(R.id.subject_tps_mark);
        tpMarkTextView.setText(Double.toString(tpMark));
        TextView averageTextView=view.findViewById(R.id.subject_average);
        EditText updateCommentEditText=view.findViewById(R.id.comment_update);
        updateCommentEditText.setText(updateComment);
        confirmDetailsButton=view.findViewById(R.id.confirm_details_button);

        builder.setView(view).setTitle("Subject Statistics")
                .setNegativeButton("Close",(dialog,id)->dismiss());
        builder.show();
        return builder.create();
    }
}