package com.hsrt.euebt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrainingManager extends AppCompatActivity {
    private ListView lvProduct;
    private List<Training> mUebungList = new ArrayList<>();
    private TrainingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);

        lvProduct = (ListView)findViewById(R.id.listview_product);


        //Add sample data for list
        //We can get data from DB, webservice here
        //mUebungList.add(new Uebung(1, "iPhone4"));
        //mUebungList.add(new Uebung(3, "iPhone4S"));
        //mUebungList.add(new Uebung(4, "iPhone5"));
        //mUebungList.add(new Uebung(5, "iPhone5S"));
        //mUebungList.add(new Uebung(6, "iPhone6"));
        //mUebungList.add(new Uebung(7, "iPhone6S"));
        //mUebungList.add(new Uebung(8, "iPhone7"));
        //mUebungList.add(new Uebung(9, "iPhone7S"));


        adapter = new TrainingListAdapter(getApplicationContext(), mUebungList);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
                //Ex: display msg with product id get from view.getTag
                Toast.makeText(getApplicationContext(), "Clicked product id =" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addItems(View v) {
        //Init adapter
        /*
        lvProduct.setAdapter(adapter);
        EditText nameUebungET= (EditText)findViewById(R.id.nameEditText);
        EditText beschreibungUebungET = (EditText)findViewById(R.id.beschreibungEditText);
        mUebungList.add(new Training(nameUebungET.getText()+""));
        clearTextField(nameUebungET);
        clearTextField(beschreibungUebungET);
        */
    }

    public void clearTextField(EditText toClear){
        toClear.setText("");
    }
}
