package com.hsrt.euebt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TestDatabaseActivity extends ListActivity {

    private TrainingsDataSource datasource;
    private ListView lvProduct;
    private List<Training> mUebungList = new ArrayList<>();
    private TrainingListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);

        lvProduct = (ListView)findViewById(R.id.listview_product);

        adapter = new TrainingListAdapter(getApplicationContext(), mUebungList);

        datasource = new TrainingsDataSource(this);
        datasource.open();

        List<String> values = datasource.getAllNames();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        //setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
      @SuppressWarnings("unchecked")
        ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();
        Training Training = null;
        switch (view.getId()) {
            case R.id.add:
                String[] Trainings = new String[] { "Piano", "English", "Sport" };
                int nextInt = new Random().nextInt(3);
                 //save the new Training to the database
                String currentGPSLocation = "";
                try {
                    currentGPSLocation = ((LocationManager)getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation(LocationManager.GPS_PROVIDER).toString();
                } catch (SecurityException e) { }
                Training = datasource.addTraining(Trainings[nextInt], currentGPSLocation);
                adapter.add(Training);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    Training = (Training) getListAdapter().getItem(0);
                    datasource.deleteTraining(Training);
                    adapter.remove(Training);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public void addItems(View v) {
        //Init adapter
        lvProduct.setAdapter(adapter);
        EditText nameUebungET= (EditText)findViewById(R.id.nameEditText);
        mUebungList.add(new Training(nameUebungET.getText()+""));
        clearTextField(nameUebungET);
    }

    public void clearTextField(EditText toClear){
        toClear.setText("");
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}