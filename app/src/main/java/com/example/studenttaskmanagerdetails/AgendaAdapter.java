package com.example.studenttaskmanagerdetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttaskmanager.R;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {
    private List<AgendaItem> agendaItems;

    public AgendaAdapter(List<AgendaItem> agendaItems){
        this.agendaItems=agendaItems;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda,parent,false);
        return new AgendaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder,int position){
        AgendaItem agendaItem=agendaItems.get(position);
        holder.dateTextView.setText(agendaItem.getDate());

        holder.eventsTextView.setText(agendaItem.getEvents().toString());
    }

    @Override
    public int getItemCount(){
        return agendaItems.size();
    }

    static class AgendaViewHolder extends RecyclerView.ViewHolder{
        TextView dateTextView;
        TextView eventsTextView;

        public AgendaViewHolder(@NonNull View itemView){
            super(itemView);
            dateTextView=itemView.findViewById(R.id.date_text);
            eventsTextView=itemView.findViewById(R.id.events_text);
        }
    }
}


