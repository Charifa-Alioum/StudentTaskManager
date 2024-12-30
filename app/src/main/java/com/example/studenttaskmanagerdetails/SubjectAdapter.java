package com.example.studenttaskmanagerdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<SubjectItem> {

    public SubjectAdapter(Context context, ArrayList<SubjectItem> subjects){
        super(context,0,subjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SubjectItem subject=getItem(position);

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,
                    parent,false);
        }

        TextView textView=convertView.findViewById(android.R.id.text1);
        textView.setText(subject.getSubjectName());

        return convertView;
    }
}
