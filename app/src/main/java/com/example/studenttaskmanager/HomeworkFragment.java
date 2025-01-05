package com.example.studenttaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.AddHomeworkActivity;
import com.example.studenttaskmanagerdetails.HomeworkAdapter;
import com.example.studenttaskmanagerdetails.HomeworkItem;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeworkFragment extends Fragment {
   private ListView listViewHomework;
   private HomeworkAdapter adapter;
   private List<HomeworkItem> homeworkItems;

    private static final int ADD_HOMEWORK_REQUEST=1;

    private PreferenceManager preferenceManager;
    private Gson gson;

    public HomeworkFragment() {
        // Required empty public constructor
    }


    public static HomeworkFragment newInstance(String param1, String param2) {
        HomeworkFragment fragment = new HomeworkFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homework,container,false);
        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();
        listViewHomework=view.findViewById(R.id.list_view_homework);
        homeworkItems=new ArrayList<>();
        adapter=new HomeworkAdapter(getContext(),homeworkItems);
        listViewHomework.setAdapter(adapter);
        loadHomeworksFromPreferences();

        FloatingActionButton fabAddHomework=view.findViewById(R.id.fab_add_homework);
        fabAddHomework.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), AddHomeworkActivity.class);
            startActivityForResult(intent,ADD_HOMEWORK_REQUEST);
        });
        listViewHomework.setOnItemLongClickListener((parent,view1,position,id)->{
            showDeleteConfirmationDialog(position);
            return true;
        });
        saveHomeworksToPreferences();
        return view;
        }

    public void addHomeworkItem(HomeworkItem item){
        homeworkItems.add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Calendar dateDue=Calendar.getInstance();
        if(requestCode==ADD_HOMEWORK_REQUEST && resultCode== AppCompatActivity.RESULT_OK){
            String homework=data.getStringExtra("Homework");
            long dateDueInMillis=data.getLongExtra("dateDue",-1);
            if(dateDueInMillis!=-1){
                dateDue.setTimeInMillis(dateDueInMillis);
            }
            HomeworkItem homeworkItem=new HomeworkItem(homework,false,dateDue);
            homeworkItems.add(homeworkItem);
            adapter.notifyDataSetChanged();
            saveHomeworksToPreferences();
            homeworkUpdates();

            Toast.makeText(getContext(), "Nouveau devoir"+homeworkItem.getName()+Boolean.toString(homeworkItem.isChecked()), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog(int position){
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Delete from module?")
                .setMessage("Are you sure to delete this element?")
                .setPositiveButton("Yes",(dialog,which)->{
                    homeworkItems.remove(position);
                    homeworkUpdates();
                    saveHomeworksToPreferences();
                })
                .setNegativeButton("No",null).show();
    }

    private void saveHomeworksToPreferences(){
        String json=gson.toJson(homeworkItems);
        preferenceManager.putString("homework_list",json);
    }

    private void loadHomeworksFromPreferences(){
        String jsonHomeworks=preferenceManager.getString("homework_list","[]");
        Type type=new TypeToken<ArrayList<HomeworkItem>>(){}.getType();
        homeworkItems=gson.fromJson(jsonHomeworks,type);

        homeworkUpdates();
    }

    private void homeworkUpdates(){
        ArrayAdapter<HomeworkItem> arrayAdapter=new HomeworkAdapter(getActivity(),homeworkItems);
        listViewHomework.setAdapter(arrayAdapter);
    }
}
