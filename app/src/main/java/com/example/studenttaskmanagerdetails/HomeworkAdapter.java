package com.example.studenttaskmanagerdetails;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studenttaskmanager.R;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<HomeworkItem> {
    private List<HomeworkItem> homeworkItems;

    public HomeworkAdapter(Context context, List<HomeworkItem> homeworkItems) {
        super(context,0,homeworkItems);
        this.homeworkItems=homeworkItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeworkItem homeworkItem = homeworkItems.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_homework,parent,false);
        }

        CheckBox checkBox=convertView.findViewById(R.id.checkbox_homework);
        TextView textView=convertView.findViewById(R.id.text_view_homework);

        textView.setText(homeworkItem.getName());
        checkBox.setChecked(homeworkItem.isChecked());

        if(homeworkItem.isChecked()){
            textView.setPaintFlags(textView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            homeworkItem.setChecked(isChecked);
            if(isChecked){
                textView.setPaintFlags(textView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textView.setPaintFlags(textView.getPaintFlags() & ~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
        return convertView;
    }
}
