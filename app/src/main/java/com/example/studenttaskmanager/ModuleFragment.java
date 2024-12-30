package com.example.studenttaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.studenttaskmanagerdetails.ChildItemsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ModuleFragment extends Fragment {
    private ListView listViewModules;
    private List<String> moduleList;
    private HashMap<String, List<String>> childItems;
    private FloatingActionButton addModuleFab;

    public ModuleFragment() {
        // Required empty public constructor
    }


    public static ModuleFragment newInstance(String param1, String param2) {
        ModuleFragment fragment = new ModuleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_module,container,false);
        listViewModules=view.findViewById(R.id.list_view_modules);
        addModuleFab=view.findViewById(R.id.addModuleFab);

        initModuleList();

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,moduleList);

        addModuleFab.setOnClickListener(v-> addModuleToList());
        listViewModules.setAdapter(adapter);
        listViewModules.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedModule=moduleList.get(position);
            showChildItems(selectedModule);
        });
        return view;
    }

    private void addModuleToList(){

    }
    private void initModuleList(){
        moduleList=new ArrayList<>();

        /*moduleList.add("Module 1");
        moduleList.add("Module 2");
        moduleList.add("Module 3");
        moduleList.add("Module 4");

        childItems=new HashMap<>();
        List<String> child1=new ArrayList<>();
        child1.add("Child Item 1");
        child1.add("Child Item 2");
        childItems.put("Module 1",child1);

        List<String> child2=new ArrayList<>();
        child2.add("Child Item 3");
        child2.add("Child Item 4");
        childItems.put("Module 2",child2);

        List<String> child3=new ArrayList<>();
        child3.add("Child Item 5");
        child3.add("Child Item 6");
        childItems.put("Module 3",child3);

        List<String> child4=new ArrayList<>();
        child4.add("Child Item 7");
        child4.add("Child Item 8");
        childItems.put("Module 4",child4);*/
    }

    private void showChildItems(String selectedModule){
        List<String> childItemsList=childItems.get(selectedModule);
        if(childItemsList!=null){
            Intent intent=new Intent(getActivity(), ChildItemsActivity.class);
            intent.putStringArrayListExtra("childItems",new ArrayList<>(childItemsList));
            startActivity(intent);
        }
    }
}