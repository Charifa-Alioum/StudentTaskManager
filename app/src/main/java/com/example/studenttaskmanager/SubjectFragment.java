package com.example.studenttaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.ColorPickerActivity;
import com.example.studenttaskmanagerdetails.SubjectDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SubjectFragment extends Fragment {
    private ListView subjectListView;
    private FloatingActionButton addSubjectFab;
    private ArrayList<String> subjectList;
    private ArrayAdapter<String> adapter;
    private String[] subjects={ };

    private static final int COLOR_PICKER_REQUEST=1;
    private static final int SUBJECT_MODIFICATION_REQUEST=2;


    public SubjectFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subject,container,false);
        subjectListView=view.findViewById(R.id.subject_list_view);
        addSubjectFab=view.findViewById(R.id.addSubjectFab);


        subjectList=new ArrayList<>();
        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,subjectList);

        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentSubject=subjectList.get(i);

                Intent intent=new Intent(getActivity(), SubjectDetailsActivity.class);
                intent.putExtra(SubjectDetailsActivity.CURRENT_SUBJECT_NAME,currentSubject);
                intent.putExtra("element_position",i);
                startActivity(intent);

            }
        });
        addSubjectFab.setOnClickListener(v ->{
            Intent intent=new Intent(getActivity(), ColorPickerActivity.class);
            startActivityForResult(intent,COLOR_PICKER_REQUEST);
            Toast.makeText(getContext(), "Nouvelle matière", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==AppCompatActivity.RESULT_OK && data!=null){
            switch (requestCode){
                case COLOR_PICKER_REQUEST:
                    String subject=data.getStringExtra("Subject");

                    subjectList.add(subject);
                    adapter.notifyDataSetChanged();

                    break;

                case SUBJECT_MODIFICATION_REQUEST:
                    String newSubjectName=data.getStringExtra(SubjectDetailsActivity.CURRENT_SUBJECT_NAME);
                    subjectList.set(requestCode,newSubjectName);
                    adapter.notifyDataSetChanged();

                    break;

                default:
                    break;
            }
        }

    }

}