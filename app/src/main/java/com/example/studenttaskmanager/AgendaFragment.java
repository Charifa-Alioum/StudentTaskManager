package com.example.studenttaskmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studenttaskmanagerdetails.AddEventDialogFragment;
import com.example.studenttaskmanagerdetails.AgendaAdapter;
import com.example.studenttaskmanagerdetails.AgendaItem;
import com.example.studenttaskmanagerinterfaces.OnEventAddedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgendaFragment extends Fragment implements OnEventAddedListener {
    private RecyclerView recyclerView;
    private AgendaAdapter agendaAdapter;
    private List<AgendaItem> agendaItems;

    public AgendaFragment() {
        // Required empty public constructor
    }

    public static AgendaFragment newInstance(String param1, String param2) {
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
        recyclerView = view.findViewById(R.id.recycler_view_agenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        agendaItems=new ArrayList<>();
        loadAgendaData();

        agendaAdapter=new AgendaAdapter(agendaItems);
        recyclerView.setAdapter(agendaAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_event);
        fab.setOnClickListener(v -> {
            AddEventDialogFragment addEventDialogFragment = new AddEventDialogFragment();
            FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,addEventDialogFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Toast.makeText(getContext(), "événement ajouté", Toast.LENGTH_SHORT).show();
        });

        //setupDaysOfWeek(view);
        return view;
    }

    private void loadAgendaData(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        Calendar endDate=(Calendar) calendar.clone();
        endDate.add(Calendar.YEAR,1);

        while (calendar.before(endDate)){
            String date=String.format("%02d %s %d",calendar.get(Calendar.DAY_OF_MONTH),
                    getMonthName(calendar.get(Calendar.MONTH)),calendar.get(Calendar.YEAR));

            List<String> events=new ArrayList<>();
            events.add("Event  for" + date);
            agendaItems.add(new AgendaItem(date,events));

            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
    }

    private String getMonthName(int month){
        String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        return months[month];
    }

    private void setupDaysOfWeek(View view){
        LinearLayout daysLayout=view.findViewById(R.id.days_of_week);
        String[] days={"M","T","W","T","F","S","S"};

        for (String day:days){
            TextView dayTextView=new TextView(getContext());
            dayTextView.setText(day);
            dayTextView.setTextSize(16);
            dayTextView.setPadding(16,16,16,16);
            daysLayout.addView(dayTextView);
        }
    }

    @Override
    public void onEventAdded(String eventName,String eventDate,String eventTime){

    }
}