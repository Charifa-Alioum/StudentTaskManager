package com.example.studenttaskmanagerdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ModuleAdapter extends ArrayAdapter<ModuleItem> {

    public ModuleAdapter(Context context, ArrayList<ModuleItem> modules){
        super(context,0,modules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ModuleItem module=getItem(position);

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,
                    parent,false);
        }

        TextView textView=convertView.findViewById(android.R.id.text1);
        textView.setText(module.getModuleName());

        return convertView;
    }
}
