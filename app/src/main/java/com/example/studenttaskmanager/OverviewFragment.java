package com.example.studenttaskmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private Button addTaskBtn;

    private ListView weeklyTask;
    private ListView todayTask;
    private ListView tomorrowTask;

    private ArrayList<String> weeklyTaskList;
    private ArrayList<String> todayTaskList;
    private ArrayList<String> tomorrowTaskList;

    private ArrayAdapter<String> adapter;

    public OverviewFragment() {

    }

    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();

        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        weeklyTask = view.findViewById(R.id.weeklyTask);
        weeklyTaskList = new ArrayList<>();
        weeklyTaskList.add("Tâche hebdomadaire 1");
        weeklyTaskList.add("Tâche hebdomadaire 2");
        weeklyTaskList.add("Tâche hebdomadaire 3");

        ArrayAdapter<String> weeklyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, weeklyTaskList);
        weeklyTask.setAdapter(weeklyAdapter);

        todayTask = view.findViewById(R.id.todayTask);
        todayTaskList = new ArrayList<>();
        todayTaskList.add("Tâche d'aujourd'hui 1");
        todayTaskList.add("Tâche d'aujourd'hui 2");
        todayTaskList.add("Tâche d'aujourd'hui 3");

        ArrayAdapter<String> todayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todayTaskList);
        todayTask.setAdapter(todayAdapter);

        tomorrowTask = view.findViewById(R.id.tomorrowTask);
        tomorrowTaskList = new ArrayList<>();
        tomorrowTaskList.add("Tâche de demain 1");
        tomorrowTaskList.add("Tâche de demain 2");
        tomorrowTaskList.add("Tâche de demain 3");

        ArrayAdapter<String> tomorrowAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tomorrowTaskList);
        tomorrowTask.setAdapter(tomorrowAdapter);

        addTaskBtn = view.findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgendaFragment();
            }
        });

        return view;
    }

    private void openAgendaFragment() {
        Fragment agendaFragment = new AgendaFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, agendaFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}