package com.example.studenttaskmanagerfeatures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SubjectViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<String>> subjectList=new MutableLiveData<>();

    public LiveData<ArrayList<String>> getSubjectList(){
        return subjectList;
    }

    public void setSubjectList(ArrayList<String> list){
        subjectList.setValue(list);
    }
}
