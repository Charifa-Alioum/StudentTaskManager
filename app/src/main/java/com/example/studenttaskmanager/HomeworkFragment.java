package com.example.studenttaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.AddHomeworkActivity;
import com.example.studenttaskmanagerdetails.HomeworkAdapter;
import com.example.studenttaskmanagerdetails.HomeworkItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeworkFragment extends Fragment {
   private ListView listViewHomework;
   private HomeworkAdapter adapter;
   private List<HomeworkItem> homeworkItems;

    private static final int ADD_HOMEWORK_REQUEST=1;

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

        listViewHomework=view.findViewById(R.id.list_view_homework);
        homeworkItems=new ArrayList<>();
        adapter=new HomeworkAdapter(getContext(),homeworkItems);
        listViewHomework.setAdapter(adapter);

        FloatingActionButton fabAddHomework=view.findViewById(R.id.fab_add_homework);
        fabAddHomework.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), AddHomeworkActivity.class);
            startActivityForResult(intent,ADD_HOMEWORK_REQUEST);
        });
        return view;
        }

    public void addHomeworkItem(HomeworkItem item){
        homeworkItems.add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==ADD_HOMEWORK_REQUEST && resultCode== AppCompatActivity.RESULT_OK){
            String subject=data.getStringExtra("Subject");

            homeworkItems.add(subject);
            adapter.notifyDataSetChanged();

            Toast.makeText(getContext(), "Nouvelle matière", Toast.LENGTH_SHORT).show();
        }
    }
}
