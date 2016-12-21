package com.hsrt.euebt;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
    private TrainingsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        datasource = new TrainingsDataSource(this);
        datasource.open();

        List<String> values = datasource.getAllNames();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();
        Training Training = null;
        switch (view.getId()) {
            case R.id.add:
                String[] Trainings = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new Training to the database
                Training = datasource.addTraining(Trainings[nextInt]);
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