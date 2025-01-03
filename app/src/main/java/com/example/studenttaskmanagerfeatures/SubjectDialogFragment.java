package com.example.studenttaskmanagerfeatures;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.studenttaskmanager.R;
import com.example.studenttaskmanagerdetails.SubjectAdapter;
import com.example.studenttaskmanagerdetails.SubjectItem;

import java.util.ArrayList;
import java.util.List;

public class SubjectDialogFragment extends DialogFragment {
    private ListView subjectsListView;
    private List<SubjectItem> subjectList;
    private OnSubjectSelectedListener listener;

    public interface OnSubjectSelectedListener{
        void onSubjectSelected(SubjectItem subject);
    }
    public SubjectDialogFragment() {
        // Required empty public constructor
    }


    public static SubjectDialogFragment newInstance(List<SubjectItem> subjects) {
        SubjectDialogFragment fragment = new SubjectDialogFragment();
        Bundle args=new Bundle();
        args.putSerializable("my_subject_list",new ArrayList<>(subjects));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            subjectList= (List<SubjectItem>) getArguments().getSerializable("my_subject_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subject_dialog,container,false);
        subjectsListView=view.findViewById(R.id.subject_to_module_list_view);
        ArrayAdapter<SubjectItem> adapter=new SubjectAdapter(getContext(), (ArrayList<SubjectItem>) subjectList);

        subjectsListView.setAdapter(adapter);

        subjectsListView.setOnItemClickListener((parent,view1,position,id)->{
            SubjectItem selectedSubject=subjectList.get(position);
            if (listener!=null){
                listener.onSubjectSelected(selectedSubject);
            }
            dismiss();
        });

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnSubjectSelectedListener){
            listener= (OnSubjectSelectedListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnSubjectSelectedListener");
        }
    }

}