package com.example.parsaniahardik.listview_edittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ListView lv;
    private CustomeAdapter customeAdapter;
    public ArrayList<EditModel> editModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.btn);

        editModelArrayList = populateList();
        customeAdapter = new CustomeAdapter(this,editModelArrayList);
        lv.setAdapter(customeAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });

    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(String.valueOf(i));
            list.add(editModel);
        }

        return list;
    }

}
