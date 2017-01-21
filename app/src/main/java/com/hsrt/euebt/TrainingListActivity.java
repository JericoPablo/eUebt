package com.hsrt.euebt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


public class TrainingListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 12;
    private TrainingAdapter wdAdapter;
    private TrainingsDataSource datasource;
    private ListView lvProduct;
    private ArrayList<Training> trainingList = new ArrayList<>();
    private TrainingExtra trainingDescriptionExtra;

    private Toolbar toolbar;
    private EditText trainingNameEditText;
    private EditText trainingDescriptionEditText;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungen);
        initToolbar();
        //lvProduct = (ListView)findViewById(R.id.listview_product);
        datasource = new TrainingsDataSource(this);
        datasource.open();
        //Trainings aus DB saugen und in die Liste einfügen
        List<String> values = datasource.getAllNames();
        setupRecyclerView();
        for(String trainingFromDB:values){
            Training newTrainingFromDB = new Training(trainingFromDB);
            trainingList.add(newTrainingFromDB);
        }
    }

    //die Toolbar wird initialisiert
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_product);

        wdAdapter = new TrainingAdapter(this, trainingList, new OnObjectClickListener() {
            @Override
            public void onObjectClick(final Training training) {
               // Item in der Liste wurde angeklickt, alert wird aufgerufen
                System.out.println("Item Clicked");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrainingListActivity.this);

                // set title
                alertDialogBuilder.setTitle("Schon gEÜBT?");

                // set dialog message
                //Anzeigen-> Zeigt Training an
                // Aktualisieren -> >Training wird als erneut geübt aktualisiert
                alertDialogBuilder
                        .setMessage("Anzeigen oder Aktualisieren?")
                        .setCancelable(false)
                        .setPositiveButton("Anzeigen",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Intent um das AnzeigeActivity zu öffnen
                                Intent showTrainingIntent = new Intent(getBaseContext(),ShowTrainingActivity.class);
                                //Aus Datenbank das Training auslesen mit den Extras
                                List<Training> trainings  = datasource.getAllTrainingsWithName(training.getName());
                                List<TrainingExtra> trainingExtras = datasource.getAllExtraDataForTraining(training.getName());
                                //das aktuellste Training aus der Liste nehmen (hoffentlich ist die letzte Training einheit in der Liste
                                //die aktuellste)
                                Training trainingToShow = trainings.get(trainings.size()-1);
                                //Training wird dem Intent mit gegeben
                                showTrainingIntent.putExtra("showTraining",trainingToShow);
                                //durch die zugehörigen Extras des Trainings gehen schauen ob description oder Image und das dem Inten zufügen
                                for (TrainingExtra trainingExtraToShow :trainingExtras) {
                                    if(trainingExtraToShow.getType().equals(TrainingExtra.ExtraType.Description)){
                                        showTrainingIntent.putExtra("showTrainingDescription",trainingExtraToShow);
                                        System.out.println("Description added to Intent");
                                    }
                                    if(trainingExtraToShow.getType().equals(TrainingExtra.ExtraType.Image))
                                        showTrainingIntent.putExtra("imageToShow",trainingExtraToShow);
                                        System.out.println("Image added to Intent");
                                }
                                startActivityForResult(showTrainingIntent, REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("Aktualisieren",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                Training trainedTraining = datasource.addTraining(training.getName(),"Longitude: -122.0840 Latitude: 37.4220");
                                trainingList.remove(training);
                                trainingList.add(trainedTraining);
                                wdAdapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        recyclerView.setAdapter(wdAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // hier alle Daten auslesen und die Liste überschreiben datasource.getAllNames();
            Training addNewTraining = (Training) data.getSerializableExtra("newTraining");
            trainingList.add(addNewTraining);
            wdAdapter.notifyDataSetChanged();
            System.out.println("GEHT REIN"+trainingList+"");

        }
    }

}