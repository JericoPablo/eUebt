package com.hsrt.euebt;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


public class TrainingListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 12;

    private TrainingsDataSource datasource;
    private ListView lvProduct;
    private List<Training> mUebungList = new ArrayList<>();
    private TrainingListAdapter adapter;

    private Toolbar toolbar;
    private EditText trainingNameEditText;
    private EditText trainingDescriptionEditText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);
        initToolbar();
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

    //die Toolbar wird initialisiert
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
  /*  public void onClick(View view) {
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
*/
    /*
    public void addItems(View v) {
        //Init adapter
        lvProduct.setAdapter(adapter);
        //EditText nameUebungET= (EditText)findViewById(R.id.nameEditText);
        mUebungList.add(new Training(nameUebungET.getText()+""));
        clearTextField(nameUebungET);
    }*/

    public void clearTextField(EditText toClear){
        toClear.setText("");
    }

    //Wenn ein neues Training hinzugefügt werden soll, wird eine neue Activity aufgerufen mittels einem Intent
    public void addTraining(View view) {
        Intent addTrainingIntent = new Intent(this, addNewTrainingActivity.class);
        startActivityForResult(addTrainingIntent, REQUEST_CODE);
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

    //Springt in die Methode wenn von er von dieser Activity in eine andere springt und wieder zurück kommt.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && requestCode == RESULT_OK) {
            // hier alle Daten auslesen und die Liste überschreiben datasource.getAllNames();
        }
    }

}