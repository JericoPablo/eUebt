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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_training);
        trainingNameTextView = (TextView) findViewById(R.id.trainingNameTextView);
        trainingDescriptionTextView = (TextView) findViewById(R.id.trainingDescriptionTextView);
        //hole Training Objekt aus dem Intent und das TrainingExtra
        showTraining = (Training) getIntent().getSerializableExtra("showTraining");
        showTrainingExtraDescription = (TrainingExtra) getIntent().getSerializableExtra("showTrainingDescription");
        // showTrainingExtraImage = (TrainingExtra) getIntent().getSerializableExtra("showTrainingImage");
        //photo = (Bitmap) getIntent().getExtras().get("trainingImage");
        //imageShow.setImageBitmap(photo);

        //Füge vom Training den Namen als Text ein
        trainingNameTextView.setText(showTraining.getName());
        //Hier sollte die Description rein da nicht unterscheidet werden kann ob Description oder Image Pfad scheiterts
        //trainingDescriptionTextView.setText(showTrainingExtraDescription.getContent());

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



    //Inten für die Camera wird ausgeführt
    public void takePicture(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    //ist nur copyPaste aus anderen Klasse nicht beachten
    //wenn oben rechts auf akzeptieren geklickt wird wird das Bild in die Storage gespeichert und der Pfad ausgelesen und in die DB geschrieben
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (item.getItemId() == R.id.actionSave) {
            datasource.addTraining(trainingNameEditText.getText().toString(), "Longitude: -122.0840 Latitude: 37.4220");
            newTraining = new Training(trainingNameEditText.getText().toString(),"Longitude: -122.0840 Latitude: 37.4220");
            if(photo!=null){
                String imagePath = ImageController.getInstance().saveImageToStorage(photo, this);
                if(imagePath!=null && imagePath.length()>=0) {
                    datasource.addTrainingExtra(trainingNameEditText.getText().toString(), TrainingExtra.ExtraType.Image, imagePath);
                }
            }
            if(trainingDescriptionEditText.getText()!=null && trainingDescriptionEditText.getText().length()>=0){
                datasource.addTrainingExtra(trainingNameEditText.getText().toString(),TrainingExtra.ExtraType.Description,trainingDescriptionEditText.getText().toString());
                descriptionExtra = new TrainingExtra(newTraining.getName(),TrainingExtra.ExtraType.Description,trainingNameEditText.getText().toString());
            }

            result = RESULT_OK;
            finish();

        }*/
        return true;
    }

    @Override
    public void finish() {
        super.finish();
    }
}
