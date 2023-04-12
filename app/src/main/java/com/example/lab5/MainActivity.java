package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    ListView lv_studentList;
    ArrayAdapter studentArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        lv_studentList = findViewById(R.id.lv_studentList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentMod studentmod;
                try {
                    studentmod = new StudentMod(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()));
                    Toast.makeText(MainActivity.this, studentmod.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error creating student", Toast.LENGTH_SHORT).show();
                    studentmod = new StudentMod(-1, "error", 0);
                }

                dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(studentmod);
                Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                ShowStudentsOnListView(dataBaseHelper);
            }
        });
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dataBaseHelper = new DataBaseHelper(MainActivity.this);
                 ShowStudentsOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, "View All Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ShowStudentsOnListView(DataBaseHelper dataBaseHelper) {
        studentArrayAdapter = new ArrayAdapter<StudentMod>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_studentList.setAdapter(studentArrayAdapter);
    }

}