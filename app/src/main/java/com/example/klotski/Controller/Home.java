package com.example.klotski.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.klotski.R;
import com.example.klotski.Services.MatrixBox;
import com.example.klotski.Services.SaveManagemnt;

import static com.example.klotski.Utiliies.Const.MAINX;
import static com.example.klotski.Utiliies.Const.MAINY;
import static com.example.klotski.Utiliies.Const.RDUCE_SIZE;
import static com.example.klotski.Utiliies.Const.U_SIZE;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private EditText unitSize;
    private EditText reduceAmount;
    private EditText mainY;
    private EditText mainX;
    private Button toGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initFunction();

    }

    private void initFunction(){
        intView();
        intVar();
        loadVar();
    }

    private void intView(){
        unitSize = findViewById(R.id.unitSize);
        reduceAmount = findViewById(R.id.reduceAmount);
        mainY = findViewById(R.id.mainY);
        toGame = findViewById(R.id.toGame);
        mainX = findViewById(R.id.mainX);
    }

    private void intVar(){
        toGame.setOnClickListener(this);
    }

    private void loadVar(){
        float lUnit = SaveManagemnt.loadFloat(this, U_SIZE);
        float lREduce = SaveManagemnt.loadFloat(this, RDUCE_SIZE);
        float loadMainY = SaveManagemnt.loadFloat(this, MAINY);
        float loadMainX = SaveManagemnt.loadFloat(this, MAINX);

        setfloat(unitSize, lUnit);
        setfloat(reduceAmount, lREduce);
        setfloat(mainY, loadMainY);
        setfloat(mainX, loadMainX);
    }

    private void setfloat(EditText editor, float x){
        if(x != -1){
            editor.setText(String.valueOf(x));
        }
    }

    private void toGameClick(){
        float vUnit;
        float vreduceAmount;
        float vMainY;
        float vMainX;

        vUnit = Float.parseFloat(unitSize.getText().toString());
        vreduceAmount = (int)Float.parseFloat(reduceAmount.getText().toString());
        vMainY = Float.parseFloat(mainY.getText().toString());
        vMainX = Float.parseFloat(mainX.getText().toString());

        MatrixBox.mainY = vMainY;
        MatrixBox.unit = vUnit;
        MatrixBox.reduceAmount = (int)vreduceAmount;
        MatrixBox.mainX = vMainX;

        saveValue(vUnit, vreduceAmount, vMainY, vMainX);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveValue(float x, float y, float z, float k){
        SaveManagemnt.saveFloat(this, x, U_SIZE);
        SaveManagemnt.saveFloat(this, y, RDUCE_SIZE);
        SaveManagemnt.saveFloat(this, z, MAINY);
        SaveManagemnt.saveFloat(this, k, MAINX);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toGame:
                toGameClick();
                break;
        }
    }
}
