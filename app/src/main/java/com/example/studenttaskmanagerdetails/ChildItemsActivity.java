package com.example.studenttaskmanagerdetails;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studenttaskmanager.R;

import java.util.List;

public class ChildItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_items);

        ListView listViewChildren=findViewById(R.id.list_view_children);
        List<String> childItems = getIntent().getStringArrayListExtra("childItems");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, childItems);

        listViewChildren.setAdapter(adapter);
        listViewChildren.setOnItemClickListener((parent, view, position, id) -> {
            String selectedChildItem = childItems.get(position);
            Toast.makeText(this, "Selected Child Item: " + selectedChildItem, Toast.LENGTH_SHORT).show();
        });
    }
}