package com.example.studenttaskmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

import com.example.studenttaskmanagerdetails.AddEventDialogFragment;
import com.example.studenttaskmanagerdetails.AgendaAdapter;
import com.example.studenttaskmanagerdetails.AgendaItem;
import com.example.studenttaskmanagerdetails.SubjectAdapter;
import com.example.studenttaskmanagerdetails.SubjectItem;
import com.example.studenttaskmanagerfeatures.AgendaItemWidgetProvider;
import com.example.studenttaskmanagerfeatures.NotificationReceiver;
import com.example.studenttaskmanagerfeatures.PreferenceManager;
import com.example.studenttaskmanagerinterfaces.OnEventAddedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgendaFragment extends Fragment implements AddEventDialogFragment.AddEventDialogListener {
    private ListView listViewEvents;
    private ArrayAdapter<AgendaItem> adapter;
    private List<AgendaItem> eventList;

    private Gson gson;
    private PreferenceManager preferenceManager;

    public AgendaFragment() {
        // Required empty public constructor
    }

    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        listViewEvents=view.findViewById(R.id.agenda_list_view);

        preferenceManager=PreferenceManager.getInstance(getContext());
        gson=new Gson();

        eventList=new ArrayList<>();
        loadEventsFromPreferences();
        adapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,eventList);

        listViewEvents.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_event);
        fab.setOnClickListener(v -> {
            AddEventDialogFragment addEventDialogFragment = new AddEventDialogFragment();
            addEventDialogFragment.setListener(this);
            addEventDialogFragment.show(getChildFragmentManager(),"AddEventDialog");

            Toast.makeText(getContext(), "Add an event to your agenda", Toast.LENGTH_SHORT).show();
        });

        saveEventsToPreferences();

        return view;
    }

    @Override
    public void onEventAdded(AgendaItem agendaItem){
        eventList.add(agendaItem);
        adapter.notifyDataSetChanged();
        scheduleNotification(agendaItem);

        updateWidget();
    }

    private void scheduleNotification(AgendaItem agendaItem){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.S){
            if (!isExactAlarmPermissionGranted()){
                Intent intent=new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            String dateTimeString=agendaItem.getDate()+" "+agendaItem.getTime();
            Log.d("ScheduleNotification","Parsing date/time : "+dateTimeString);

            Date date=sdf.parse(dateTimeString);
            if (date!=null){
                long triggerAtMillis=date.getTime();
                Intent intent=new Intent(getActivity(), NotificationReceiver.class);
                intent.putExtra("title",agendaItem.getTitle());

                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),0,
                        intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager= (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                if (alarmManager!=null){
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,triggerAtMillis,pendingIntent);
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
            Log.e("ScheduleNotification","Failed to parse date/time");
            throw new RuntimeException("a parsing issue");
        }
    }

    private boolean isExactAlarmPermissionGranted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true;
        } else {
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
            return alarmManager.canScheduleExactAlarms();
        }
    }

    private void eventUpdates(){
        ArrayAdapter<AgendaItem> arrayAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,eventList);
        listViewEvents.setAdapter(arrayAdapter);
    }

    private void saveEventsToPreferences(){
        String json=gson.toJson(eventList);
        preferenceManager.putString("event_list",json);
    }

    private void loadEventsFromPreferences() {
        String jsonSubjects = preferenceManager.getString("event_list", "[]");
        Type type = new TypeToken<ArrayList<AgendaItem>>() {}.getType();
        eventList = gson.fromJson(jsonSubjects, type);

        Log.d("AgendaFragment", "Loaded events count: " +
                (eventList != null ? eventList.size() : 0));

        if (eventList != null && !eventList.isEmpty()) {
            adapter.clear();
            adapter.addAll(eventList);
            adapter.notifyDataSetChanged();
            listViewEvents.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "Your agenda is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWidget(){
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(getContext(),
                AgendaItemWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_event_list);
    }
}