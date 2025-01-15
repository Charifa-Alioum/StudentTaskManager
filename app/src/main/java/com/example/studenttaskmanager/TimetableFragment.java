package com.example.studenttaskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studenttaskmanagerdetails.ScheduleAdapter;
import com.example.studenttaskmanagerdetails.ScheduleItem;
import com.example.studenttaskmanagerdetails.SubjectAdapter;
import com.example.studenttaskmanagerdetails.SubjectItem;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.example.studenttaskmanagerfeatures.SubjectDialogFragment;
import com.example.studenttaskmanagerfeatures.SubjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableFragment extends Fragment implements SubjectDialogFragment.OnSubjectSelectedListener, SubjectDialogFragment.ScheduleDialogListener{

    /*private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private List<ScheduleItem> scheduleList;*/
    private CalendarView calendarView;
    private ListView listView;

    private Map<LocalDate,List<ScheduleItem>> subjectItemsMap=new HashMap<>();
    private ArrayAdapter<ScheduleItem> adapter;

    private SubjectViewModel viewModel;

    private SubjectDialogFragment.OnSubjectSelectedListener listener;
    private SubjectDialogFragment.ScheduleDialogListener scheduleListener;

    private EditText subjectEditText;

    private LocalDate selectedDate;
    private PreferenceManager preferenceManager;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();
        /*recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);*/

        calendarView=view.findViewById(R.id.calendar_timetable);
        listView=view.findViewById(R.id.list_view);
        FloatingActionButton addButton = view.findViewById(R.id.add_button);

        calendarView.setOnDateChangeListener((view1,year,month,dayOfMonth) ->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDate=LocalDate.of(year,month+1,dayOfMonth);
            }
            updateListView(selectedDate);
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectDialogFragment dialogFragment=new SubjectDialogFragment();
                //dialogFragment.setListener(this);
                showAddScheduleDialog();
            }
        });
        adapter=new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, new ArrayList());
        listView.setAdapter(adapter);

        viewModel=new ViewModelProvider(requireActivity()).get(SubjectViewModel.class);
        return view;
    }

    private void showAddScheduleDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_add_schedule, null);

        subjectEditText = dialogView.findViewById(R.id.subject_edit_text);
        final EditText dateEditText = dialogView.findViewById(R.id.date_edit_text);
        final EditText startTimeEditText = dialogView.findViewById(R.id.start_time_edit_text);
        final EditText endTimeEditText = dialogView.findViewById(R.id.end_time_edit_text);

        subjectEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubjectPickerDialog(subjectEditText);
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dateEditText);
            }
        });

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(startTimeEditText);
            }
        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(endTimeEditText);
            }
        });

        new AlertDialog.Builder(getContext())
                .setTitle("Add a subject to your timetable")
                .setView(dialogView)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subject = subjectEditText.getText().toString();
                        String date = dateEditText.getText().toString();
                        String startTime = startTimeEditText.getText().toString();
                        String endTime= endTimeEditText.getText().toString();

                        if (!subject.isEmpty() && !date.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                            ScheduleItem scheduleItem=new ScheduleItem(subject,date,startTime,endTime);
                            scheduleListener.onScheduleItemAdded(scheduleItem);
                            Toast.makeText(getContext(), "Programme ajouté", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final EditText timeEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeEditText.setText(hourOfDay + ":" + String.format("%02d", minute));
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void showSubjectPickerDialog(final EditText subjectEditText){
        List<SubjectItem> subjects=getSubjects();
        SubjectDialogFragment dialog=SubjectDialogFragment.newInstance(subjects);
        dialog.setListener(this);
        dialog.setTargetFragment(TimetableFragment.this,1);
        dialog.show(getParentFragmentManager(),"SubjectDialog");

        Toast.makeText(getContext(),"pick a subject",Toast.LENGTH_SHORT).show();
    }

    public List<SubjectItem> getSubjects(){
        String jsonSubjects=preferenceManager.getString("subject_list","[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        return new Gson().fromJson(jsonSubjects,type);
    }

    @Override
    public void onSubjectSelected(SubjectItem subject){
        String scheduleSubject=subject.getSubjectName();
        View view=getView();
        assert view != null;
        EditText subjectEditText=view.findViewById(R.id.subject_edit_text);
        subjectEditText.setText(scheduleSubject);
    }

    public void updateSubject(SubjectItem subjectItem){
        String scheduleSubject=subjectItem.getSubjectName();
        if (subjectEditText !=null){
            subjectEditText.setText(scheduleSubject);
            Log.d("TimetableFragment","mission complete");
        }
        else {
            Log.e("TimetableFragment","problem here");
            Toast.makeText(getContext(),"EdiitText pas initiailisé",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListView(LocalDate localDate){
        List<ScheduleItem> scheduleItems=subjectItemsMap.getOrDefault(localDate,new ArrayList<>());
        adapter.clear();
        adapter.addAll(scheduleItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onScheduleItemAdded(ScheduleItem scheduleItem){
        List<ScheduleItem> scheduleItems=subjectItemsMap.getOrDefault(selectedDate,new ArrayList<>());
        scheduleItems.add(scheduleItem);
        subjectItemsMap.put(selectedDate,scheduleItems);
        updateListView(selectedDate);
    }
}
