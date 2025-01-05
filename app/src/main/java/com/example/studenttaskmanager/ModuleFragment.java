package com.example.studenttaskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.ChildItemsActivity;
import com.example.studenttaskmanagerdetails.ModuleAdapter;
import com.example.studenttaskmanagerdetails.ModuleItem;
import com.example.studenttaskmanagerdetails.SubjectAdapter;
import com.example.studenttaskmanagerdetails.SubjectItem;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ModuleFragment extends Fragment {
    private ListView listViewModules;
    private List<ModuleItem> moduleList;
    private HashMap<String, List<String>> childItems;
    private FloatingActionButton addModuleFab;
    private ArrayAdapter<ModuleItem> adapter;

    private ProgressBar modulesProgressBar;
    private TextView modulesAverageTextView;

    private PreferenceManager preferenceManager;
    private Gson gson;

    private static final int OPEN_MODULE_REQUEST=1;

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
        modulesProgressBar=view.findViewById(R.id.modules_average);
        modulesAverageTextView=view.findViewById(R.id.average_value_modules);
        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();

        moduleList=new ArrayList<>();
        adapter=new ModuleAdapter(getContext(), (ArrayList<ModuleItem>) moduleList);

        listViewModules.setAdapter(adapter);
        loadModulesFromPreferences();
        listViewModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModuleItem moduleName=moduleList.get(i);

                Intent intent=new Intent(getContext(),ChildItemsActivity.class);
                intent.putExtra("MODULE_NAME",moduleName.getModuleName());
                startActivity(intent);
            }
        });
        listViewModules.setOnItemLongClickListener((parent,view1,position,id)->{
            showDeleteConfirmationDialog(position);
            return true;
        });

        addModuleFab.setOnClickListener(v-> addModuleToList());
        saveModulesToPreferences();
        calculateModulesAverage();
        return view;
    }

    private void addModuleToList(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Add a module");

        final EditText moduleInput=new EditText(getContext());
        final EditText moduleCredit=new EditText(getContext());
        moduleInput.setHint("Nom du module");
        moduleCredit.setHint("Nombre de crédits");

        LinearLayout layout=new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16,16,16,16);
        layout.addView(moduleInput);
        layout.addView(moduleCredit);

        builder.setView(layout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String moduleName=moduleInput.getText().toString().trim();
                String stringCredit=moduleCredit.getText().toString().trim();
                int credits=Integer.parseInt(stringCredit);
                ModuleItem moduleItem=new ModuleItem(moduleName,credits);
                if (!moduleName.isEmpty() && !stringCredit.isEmpty()){
                    moduleList.add(moduleItem);
                    adapter.notifyDataSetChanged();
                    saveModulesToPreferences();
                    moduleUpdates();
                }
                else {
                    Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void saveModulesToPreferences(){
        String json=gson.toJson(moduleList);
        preferenceManager.putString("module_list",json);
    }

    private void loadModulesFromPreferences(){
        String jsonSubjects=preferenceManager.getString("module_list","[]");
        Type type=new TypeToken<ArrayList<ModuleItem>>(){}.getType();
        moduleList=gson.fromJson(jsonSubjects,type);

        moduleUpdates();
    }

    private void moduleUpdates(){
        ArrayAdapter<ModuleItem> arrayAdapter=new ModuleAdapter((Context) getActivity(), (ArrayList<ModuleItem>) moduleList);
        listViewModules.setAdapter(arrayAdapter);
    }

    private void showDeleteConfirmationDialog(int position){
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Delete from module?")
                .setMessage("Are you sure to delete this element?")
                .setPositiveButton("Yes",(dialog,which)->{
                    moduleList.remove(position);
                    moduleUpdates();
                    saveModulesToPreferences();
                })
                .setNegativeButton("No",null).show();
    }

    private void calculateModulesAverage(){
        double average;
        double averageSum=0;
        int numberOfCredits=0;
        for (ModuleItem moduleItem : moduleList){
            double moduleAverage=preferenceManager.getDouble("module average for : "+moduleItem.getModuleName(),1);
            averageSum=averageSum+(moduleAverage*moduleItem.getCreditNumber());
            numberOfCredits=numberOfCredits+moduleItem.getCreditNumber();
        }
        average=averageSum/numberOfCredits;
        modulesAverageTextView.setText(Float.toString((float) average));
        modulesProgressBar.setProgress((int) average);
    }

}