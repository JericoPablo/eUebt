package com.hsrt.euebt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Tugrul on 10.01.2017.
 */

public class addNewTrainingActivity extends AppCompatActivity {

    private ArrayList<Training> trainingList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_training);
    }


    public void addItems(View v) {
        EditText trainingNameEditText = (EditText)findViewById(R.id.trainingNameEditText);
        EditText trainingDescriptionEditText = (EditText)findViewById(R.id.trainingDescriptionEditText);
        trainingList.add(new Training(trainingNameEditText.getText()+"",trainingDescriptionEditText.getText()+""));
        clearTextField(trainingNameEditText);
        clearTextField(trainingDescriptionEditText);
        Intent nextIntent = new Intent(this, TrainingListActivity.class);
        nextIntent.putExtra("trainingList", trainingList);
        this.startActivity(nextIntent);

    }
    public void clearTextField(EditText toClear){
        toClear.setText("");
    }


}
