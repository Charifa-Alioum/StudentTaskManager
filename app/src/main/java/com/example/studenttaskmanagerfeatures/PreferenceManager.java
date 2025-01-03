package com.example.studenttaskmanagerfeatures;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.studenttaskmanagerdetails.SubjectItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {
    private static final String PREF_NAME="app_preferences";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static PreferenceManager instance;
    private Gson gson;

    private PreferenceManager(Context context){
        sharedPreferences= context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();
    }

    public static synchronized PreferenceManager getInstance(Context context){
        if (instance==null){
            instance=new PreferenceManager(context.getApplicationContext());
        }
        return instance;
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putDouble(String key, double value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat(key, (float) value);
        editor.apply();
    }

    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue){
        return sharedPreferences.getString(key,defaultValue);
    }

    public int getInt(String key, int defaultValue){
        return sharedPreferences.getInt(key,defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return sharedPreferences.getBoolean(key,defaultValue);
    }

    public Double getDouble(String key,double defaultValue){
        return (double) sharedPreferences.getFloat(key, (float) defaultValue);
    }

    public void remove(String key){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public boolean contains(String key){
        return sharedPreferences.contains(key);
    }

    public void clear(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getSubjectsJson(){
        return sharedPreferences.getString("subject_list","[]");
    }

    public void saveSubjects(String moduleId, List<SubjectItem> subjects){
        String jsonSubjects=gson.toJson(subjects);
        sharedPreferences.edit().putString("subjects_"+moduleId,jsonSubjects).apply();
    }

    public List<SubjectItem> loadSubjects(String moduleId){
        String jsonSubjects=sharedPreferences.getString("subjects_"+moduleId,"[]");
        Type type=new TypeToken<ArrayList<SubjectItem>>(){}.getType();
        return gson.fromJson(jsonSubjects,type);
    }

}
