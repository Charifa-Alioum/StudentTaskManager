package com.example.studenttaskmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.ColorPickerActivity;
import com.example.studenttaskmanagerdetails.SubjectAdapter;
import com.example.studenttaskmanagerdetails.SubjectDetailsActivity;
import com.example.studenttaskmanagerdetails.SubjectItem;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.example.studenttaskmanagerfeatures.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.security.auth.Subject;


public class SubjectFragment extends Fragment {
    private ListView subjectListView;
    private FloatingActionButton addSubjectFab;
    private ArrayList<SubjectItem> subjectList;
    private SubjectAdapter adapter;

    private static final int COLOR_PICKER_REQUEST=1;
    private static final int SUBJECT_MODIFICATION_REQUEST=2;

    private SubjectViewModel viewModel;

    private PreferenceManager preferenceManager;
    private Gson gson;

    public SubjectFragment() {
        // Required empty public constructor
    }

    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectList=new ArrayList<>();
        viewModel=new ViewModelProvider(requireActivity()).get(SubjectViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subject,container,false);
        subjectListView=view.findViewById(R.id.subject_list_view);
        addSubjectFab=view.findViewById(R.id.addSubjectFab);

        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();

        subjectList=new ArrayList<>();
        adapter=new SubjectAdapter(getActivity(), subjectList);

        subjectListView.setAdapter(adapter);
        loadSubjectsFromPreferences();
        subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SubjectItem currentSubject=subjectList.get(i);

                Intent intent=new Intent(getActivity(), SubjectDetailsActivity.class);
                intent.putExtra(SubjectDetailsActivity.CURRENT_SUBJECT_NAME,currentSubject.getSubjectName());
                intent.putExtra("color",currentSubject.getColor());
                intent.putExtra("ccCheck",currentSubject.isCcCheckbox());
                intent.putExtra("ccMark",currentSubject.getCcMark());
                intent.putExtra("snCheck",currentSubject.isSnCheckbox());
                intent.putExtra("snMark",currentSubject.getSnMark());
                intent.putExtra("tpCheck",currentSubject.isTpCheckbox());
                intent.putExtra("tpMark",currentSubject.getTpMark());
                intent.putExtra("comment",currentSubject.getCommentZone());
                preferenceManager.putString("subject_comment",currentSubject.getCommentZone());

                intent.putExtra("element_position",i);
                startActivityForResult(intent,SUBJECT_MODIFICATION_REQUEST);
            }
        });
        subjectListView.setOnItemLongClickListener((parent,view1,position,id)->{
            showDeleteConfirmationDialog(position);
            return true;
        });
        addSubjectFab.setOnClickListener(v ->{
            Intent intent=new Intent(getActivity(), ColorPickerActivity.class);
            startActivityForResult(intent,COLOR_PICKER_REQUEST);
        });

        saveSubjectsToPreferences();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==AppCompatActivity.RESULT_OK && data!=null){
            switch (requestCode){
                case COLOR_PICKER_REQUEST:
                    String subject=data.getStringExtra("Subject");

                    subjectList.add(new SubjectItem(subject, Color.BLUE,false,
                            0,false,0,false,0,
                            ""));
                    adapter.notifyDataSetChanged();
                    saveSubjectsToPreferences();
                    subjectUpdates();

                    Toast.makeText(getContext(), "Nouvelle matière", Toast.LENGTH_SHORT).show();

                    break;

                case SUBJECT_MODIFICATION_REQUEST:
                    String newSubjectName=data.getStringExtra(SubjectDetailsActivity.CURRENT_SUBJECT_NAME);
                    int newSubjectColor=data.getIntExtra("color",Color.BLUE);
                    boolean newSubjectCCCheckbox=data.getBooleanExtra("ccCheck",false);
                    double newSubjectCCMark=data.getDoubleExtra("ccMark",0);
                    boolean newSubjectSNCheckbox=data.getBooleanExtra("snCheck",false);
                    double newSubjectSNMark=data.getDoubleExtra("snMark",0);
                    boolean newSubjectTPCheckbox=data.getBooleanExtra("tpCheck",false);
                    double newSubjectTPMark=data.getDoubleExtra("tpMark",0);
                    String comment=data.getStringExtra("comment");

                    int index=data.getIntExtra("element_position",-1);
                    if(index>=0 && index<subjectList.size()){
                        subjectList.set(index,new SubjectItem(newSubjectName,newSubjectColor,newSubjectCCCheckbox,
                                newSubjectCCMark,newSubjectSNCheckbox,newSubjectSNMark,newSubjectTPCheckbox,newSubjectTPMark,
                                comment));
                        adapter.notifyDataSetChanged();
                        saveSubjectsToPreferences();
                        subjectUpdates();
                    }

                    break;

                default:
                    break;
            }
        }
    }

    private void subjectUpdates(){
        ArrayAdapter<SubjectItem> arrayAdapter=new SubjectAdapter(getActivity(), subjectList);
        subjectListView.setAdapter(arrayAdapter);
    }

    private void saveSubjectsToPreferences(){
        String json=gson.toJson(subjectList);
        preferenceManager.putString("subject_list",json);
    }

    private void loadSubjectsFromPreferences(){
        String jsonSubjects=preferenceManager.getString("subject_list","[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        subjectList=gson.fromJson(jsonSubjects,type);

        subjectUpdates();
    }

    public List<SubjectItem> getSubjects(){
        String jsonSubjects=preferenceManager.getString("subject_list","[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        return gson.fromJson(jsonSubjects,type);
    }

    private void showDeleteConfirmationDialog(int position){
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Delete from module?")
                .setMessage("Are you sure to delete this element?")
                .setPositiveButton("Yes",(dialog,which)->{
                    subjectList.remove(position);
                    subjectUpdates();
                    saveSubjectsToPreferences();
                })
                .setNegativeButton("No",null).show();


    }

}