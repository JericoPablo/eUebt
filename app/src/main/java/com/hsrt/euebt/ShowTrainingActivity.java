package com.hsrt.euebt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tugrul on 20.01.2017.
 */

public class ShowTrainingActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 1898;

    private ImageView imageShow;
    private Toolbar toolbar;
    private int result = RESULT_CANCELED;

    private TextView trainingNameTextView;
    private TextView trainingDescriptionTextView;
    private TrainingsDataSource datasource;
    private Bitmap photo;
    private Training showTraining;
    private TrainingExtra showTrainingExtraDescription;
    private TrainingExtra showTrainingExtraImage;
    private TrainingExtra descriptionExtra;
    private TextView trainingGeoDataTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_training);
        trainingNameTextView = (TextView) findViewById(R.id.trainingNameTextView);
        trainingDescriptionTextView = (TextView) findViewById(R.id.trainingDescriptionTextView);
        trainingGeoDataTextView = (TextView) findViewById(R.id.trainingGeoDataTextView);
        imageShow =  (ImageView) findViewById(R.id.image_show);
        //hole Training Objekt aus dem Intent und die TrainingExtras
        showTraining = (Training) getIntent().getSerializableExtra("showTraining");
        showTrainingExtraDescription = (TrainingExtra) getIntent().getSerializableExtra("showTrainingDescription");
        showTrainingExtraImage = (TrainingExtra) getIntent().getSerializableExtra("imageToShow");

        // zeige Bild an wenn eins existiert
        if(showTrainingExtraImage!=null) {
            photo = ImageController.getInstance().loadImageFromStorage(showTrainingExtraImage.getContent());
            if(photo!=null){
                imageShow.setImageBitmap(photo);
            }
        }
        //Füge vom Training den Namen als Text ein
        trainingNameTextView.setText("Training Name: "+showTraining.getName());
        //wenn description existiert zeige an
        if(showTrainingExtraDescription!=null) {
            trainingDescriptionTextView.setText("Description: "+showTrainingExtraDescription.getContent());
        }
        if(trainingGeoDataTextView!=null) {
            trainingGeoDataTextView.setText("Ort: "+showTraining.getLocation());
        }

        initToolbar();
        datasource = new TrainingsDataSource(this);
        datasource.open();
    }

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

    //momentan nichts
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    //wenn oben links auf abbrechen geklickt wird springt es wieeder in die vorherige Activity zurück
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void finish() {
        super.finish();
    }
}
