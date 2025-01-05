package com.example.studenttaskmanagerfeatures;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.studenttaskmanagerdetails.SubjectItem;

import java.util.ArrayList;
import java.util.List;

public class SubjectViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<SubjectItem>> subjectList=new MutableLiveData<>();

    public LiveData<ArrayList<SubjectItem>> getSubjectList(){
        return subjectList;
    }

    public void addSubjectItem(SubjectItem subject){
        ArrayList<SubjectItem> currentList=subjectList.getValue();
        if (currentList!=null){
            currentList.add(subject);
            subjectList.setValue(currentList);
        }
    }

    public void updateSubjectItem(int index,SubjectItem subject){
        ArrayList<SubjectItem> currentList=subjectList.getValue();
        if (currentList!=null && index>=0 && index<currentList.size()){
            currentList.set(index,subject);
            subjectList.setValue(currentList);
        }
    }
}
