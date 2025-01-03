package com.example.studenttaskmanagerdetails;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.studenttaskmanager.R;
import com.example.studenttaskmanager.SubjectFragment;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.example.studenttaskmanagerfeatures.SubjectDialogFragment;
import com.example.studenttaskmanagerfeatures.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChildItemsActivity extends AppCompatActivity implements SubjectDialogFragment.OnSubjectSelectedListener{
    private ListView listViewChildren;
    private List<SubjectItem> childItems;
    private ArrayAdapter<SubjectItem> adapter;
    private FloatingActionButton addSubjectFab;
    private SubjectViewModel viewModel;

    private PreferenceManager preferenceManager;
    private Gson gson;

    private String modulesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_items);
        modulesName=getIntent().getStringExtra("MODULE_NAME");
        preferenceManager=PreferenceManager.getInstance(this);
        listViewChildren=findViewById(R.id.list_view_children);
        addSubjectFab=findViewById(R.id.fab_add_subject_to_module);

        preferenceManager=PreferenceManager.getInstance(this);
        gson=new Gson();

        childItems = preferenceManager.loadSubjects(modulesName);

        adapter = new SubjectAdapter((Context) this, (ArrayList<SubjectItem>) childItems);

        listViewChildren.setAdapter(adapter);
        loadSubjectsFromModulePreferences();
        viewModel=new ViewModelProvider(this).get(SubjectViewModel.class);


        setTitle(modulesName);
        listViewChildren.setOnItemClickListener((parent, view, position, id) -> {
            SubjectItem selectedChildItem = childItems.get(position);
            showDetailsDialog(selectedChildItem);
            Toast.makeText(this, "Selected Child Item: " + selectedChildItem.getSubjectName(), Toast.LENGTH_SHORT).show();
        });
        listViewChildren.setOnItemLongClickListener((parent,view,position,id)->{
            showDeleteConfirmationDialog(position);
            return true;
        });

        TextView moduleNameTextView=findViewById(R.id.module_name_text_view);
        try {
            if (getIntent()!=null){
                String moduleName=getIntent().getStringExtra("MODULE_NAME");
                if (moduleName!=null){
                    moduleNameTextView.setText(moduleName);
                }
                else {
                    moduleNameTextView.setText("Indisponible");
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"erreur d'affichage",Toast.LENGTH_SHORT).show();
        }

        addSubjectFab.setOnClickListener(v -> addSubjectToModule());
        saveSubjectsToModulePreferences();
    }

    private void addSubjectToModule(){
        List<SubjectItem> subjects=getSubjects();
        SubjectDialogFragment dialog=SubjectDialogFragment.newInstance(subjects);
        dialog.show(getSupportFragmentManager(),"SubjectDialog");

        saveSubjectsToModulePreferences();
        subjectToModuleUpdates();
    }

    public List<SubjectItem> getSubjects(){
        String jsonSubjects=preferenceManager.getString("subject_list","[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        return new Gson().fromJson(jsonSubjects,type);
    }

    @Override
    public void onSubjectSelected(SubjectItem subject){
        childItems.add(subject);
        adapter.notifyDataSetChanged();
        //preferenceManager.saveSubjects(modulesName,childItems);
        saveSubjectsToModulePreferences();
        subjectToModuleUpdates();
    }

    private void loadSubjectsFromModulePreferences(){
        String jsonSubjects=preferenceManager.getString("subjects_"+modulesName,"[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        childItems=gson.fromJson(jsonSubjects,type);

        subjectToModuleUpdates();
    }
    private void saveSubjectsToModulePreferences(){
        String json=gson.toJson(childItems);
        preferenceManager.putString("subjects_"+modulesName,json);
    }
    private void subjectToModuleUpdates(){
        ArrayAdapter<SubjectItem> arrayAdapter=new SubjectAdapter((Context) this, (ArrayList<SubjectItem>) childItems);
        listViewChildren.setAdapter(arrayAdapter);
    }

    private void showDeleteConfirmationDialog(int position){
        new AlertDialog.Builder(this)
                .setTitle("Delete from module?")
                .setMessage("Are you sure to delete this element?")
                .setPositiveButton("Yes",(dialog,which)->{
                    childItems.remove(position);
                    subjectToModuleUpdates();
                    saveSubjectsToModulePreferences();
                })
                .setNegativeButton("No",null).show();
    }

    private void showDetailsDialog(SubjectItem subject){
        DetailsDialog dialog=new DetailsDialog();
        Bundle args=new Bundle();
        args.putString("subject_name",subject.getSubjectName());
        args.putBoolean("cc_checkbox",subject.isCcCheckbox());
        args.putDouble("cc_mark",subject.getCcMark());
        args.putBoolean("sn_checkbox",subject.isSnCheckbox());
        args.putDouble("sn_mark",subject.getSnMark());
        args.putBoolean("tp_checkbox",subject.isTpCheckbox());
        args.putDouble("tp_mark",subject.getTpMark());
        args.putString("comment",subject.getCommentZone());

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(),"DetailsDialog");
    }
}