package com.example.studenttaskmanagerdetails;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.studenttaskmanager.R;

import java.util.Calendar;

public class AddEventDialogFragment extends DialogFragment {
    private EditText eventNameEditText;
    private TextView dateTextView;
    private TextView timeTextView;
    private CheckBox emergencyCheckbox;
    private Calendar selectedDateTime;
    private AddEventDialogListener listener;

    public interface AddEventDialogListener{
        void onEventAdded(AgendaItem agendaItem);
    }

    public void setListener(AddEventDialogListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(requireActivity());
        View view=requireActivity().getLayoutInflater().inflate(R.layout.dialog_add_event,null);
        eventNameEditText = view.findViewById(R.id.event_name);
        dateTextView = view.findViewById(R.id.date_text);
        timeTextView = view.findViewById(R.id.time_text);
        emergencyCheckbox = view.findViewById(R.id.emergency_level);

        selectedDateTime=Calendar.getInstance();
        dateTextView.setOnClickListener(v->showDatePicker());
        timeTextView.setOnClickListener(v->showTimePicker());

        builder.setView(view)
                .setTitle("Add an event")
                .setPositiveButton("Add",(dialog, which) ->{
                    String title=eventNameEditText.getText().toString();
                    String date=dateTextView.getText().toString();
                    String time=timeTextView.getText().toString();
                    boolean isUrgent=emergencyCheckbox.isChecked();

                    AgendaItem item=new AgendaItem(title,date,time,isUrgent);
                    if (listener!=null){
                        listener.onEventAdded(item);
                    }
                })
                .setNegativeButton("Cancel", (dialog,which) -> dialog.dismiss());
        return builder.create();
    }
    /*public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_event,container,false);

        eventNameEditText = view.findViewById(R.id.event_name);
        dateTextView = view.findViewById(R.id.date_text);
        timeTextView = view.findViewById(R.id.time_text);
        emergencyCheckbox = view.findViewById(R.id.emergency_level);
        Button addEventButton=view.findViewById(R.id.add_event_button);

        selectedDateTime=Calendar.getInstance();

        dateTextView.setOnClickListener(v->showDatePicker());
        timeTextView.setOnClickListener(v->showTimePicker());
        //emergencyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> );

        addEventButton.setOnClickListener(v->addEvent());

        return view;

    }*/

    private void showDatePicker(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                getContext(),
                (view1,year,month,dayOfMonth) ->{
                    selectedDateTime.set(Calendar.YEAR,year);
                    selectedDateTime.set(Calendar.MONTH,month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    dateTextView.setText(String.format("%02d/%02d/%d",dayOfMonth,month+1,year));
                },
                selectedDateTime.get(Calendar.YEAR),selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view12, hourOfDay, minute) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute);
                    timeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY), selectedDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void addEvent(){
        String eventName=eventNameEditText.getText().toString();
        if(!eventName.isEmpty()){
            //il faut l'ajouter à l'agenda
            dismiss();
        }
    }
}

