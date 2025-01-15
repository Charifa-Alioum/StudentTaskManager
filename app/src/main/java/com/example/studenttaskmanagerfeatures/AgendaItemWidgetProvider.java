package com.example.studenttaskmanagerfeatures;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.icu.text.ListFormatter;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.studenttaskmanager.R;
import com.example.studenttaskmanagerdetails.AgendaItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AgendaItemWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        String jsonEvents = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("event_list", "[]");
        Type type=new TypeToken<ArrayList<AgendaItem>>(){}.getType();
        ArrayList<AgendaItem> eventList=new Gson().fromJson(jsonEvents,type);

        StringBuilder eventsText = new StringBuilder();
        for (AgendaItem agendaItem : eventList){
            eventsText.append(agendaItem.getTitle()).append("\n");
        }
        views.setTextViewText(R.id.widget_event_list, eventsText.toString());

        appWidgetManager.updateAppWidget(appWidgetId,views);
    }
}
