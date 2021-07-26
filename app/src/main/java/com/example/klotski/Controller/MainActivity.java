package com.example.klotski.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.klotski.Classes.Box;
import com.example.klotski.R;
import com.example.klotski.Services.MatrixBox;
import com.example.klotski.Services.SaveManagemnt;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.klotski.Utiliies.Const.S_MOVES;
import static com.example.klotski.Utiliies.Const.S_MOVES_AUTO;
import static com.example.klotski.Utiliies.Const.S_Positions_LIST;
import static com.example.klotski.Utiliies.Const.S_Positions_LIST_AUTO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //view
    private Button saveBtn;
    private Button restartGameBtn;
    private Button loadBtn;
    private TextView movesText;

    //var UI
    private String label = "Moves : \n";
    private int oldMovesCount = 0;
    private int movesCount = 0;
    private boolean isLoaded = false;
    private float[][] oldPositions = null;

    //boxes
    private RelativeLayout gameLayout;
    private ImageView testBox;
    private ImageView redBox;
    private ImageView yellowBox;
    private ImageView blueBox1;
    private ImageView blueBox2;
    private ImageView blueBox3;
    private ImageView blueBox4;
    private ImageView greenBox1;
    private ImageView greenBox2;
    private ImageView greenBox3;
    private ImageView greenBox4;
    private ImageView wallTop;
    private ImageView wallBottom1;
    private ImageView wallRight;
    private ImageView wallLeft;
    private ImageView wallBottom2;
    private ImageView wallBottom3;

    //var
    private Box[] boxes;
    private int selectedBox = -1;
    private int reduceAmount;
    private float unit ;
    private float mainX;
    private float mainY;
    private boolean isRestaured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(!isRestaured){

            //get height and width of the screen
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            //percent
            float xPer = 5/100f;
            float yPer = 5/100f;

            //width
            mainX = width * (xPer);
            unit = (int)((width - (2 * mainX)) / 5f);
            mainX += unit / 2;

            //height
            mainY = height - height * (yPer) - unit * 6f;
            mainY += unit / 2;

            MatrixBox.unit = unit;
            MatrixBox.mainX = mainX;
            MatrixBox.mainY = mainY;


            reduceAmount = MatrixBox.reduceAmount;

            initView();
            initVar();
            initGame();
            restoreGame();
            isRestaured = true;
        }


        super.onWindowFocusChanged(hasFocus);
    }

    private void initView(){
        saveBtn = findViewById(R.id.saveBtn);
        restartGameBtn = findViewById(R.id.restartGameBtn);
        loadBtn = findViewById(R.id.loadBtn);
        movesText = findViewById(R.id.mouvesText);


        gameLayout = findViewById(R.id.gameLayout);
        testBox = findViewById(R.id.testBox);
        redBox = findViewById(R.id.redBox);
        yellowBox = findViewById(R.id.yellowBox);
        blueBox1 = findViewById(R.id.blueBox1);
        blueBox2 = findViewById(R.id.blueBox2);
        blueBox3 = findViewById(R.id.blueBox3);
        blueBox4 = findViewById(R.id.blueBox4);
        greenBox1 = findViewById(R.id.greenBox1);
        greenBox2 = findViewById(R.id.greenBox2);
        greenBox3 = findViewById(R.id.greenBox3);
        greenBox4 = findViewById(R.id.greenBox4);
        wallTop = findViewById(R.id.wallTop);
        wallBottom1 = findViewById(R.id.wallBottom);
        wallRight = findViewById(R.id.wallRight);
        wallLeft = findViewById(R.id.wallLeft);
        wallBottom2 = findViewById(R.id.wallOut);
        wallBottom3 = findViewById(R.id.wallBottom3);
    }

    private void initVar(){
        saveBtn.setOnClickListener(this);
        restartGameBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
    }

    //-------------------------------------------------
    //init Game

    private void restoreGame(){
        oldPositions = SaveManagemnt.loadList(this, S_Positions_LIST_AUTO);
        movesCount = SaveManagemnt.loadMoves(this, S_MOVES_AUTO);
        changeText();
        if(oldPositions != null){
            loadPositions();
        }else{
            oldPositions = new float[16][2];
        }
    }

    private void initGame(){
        initBox();
        initPosition();

        testBox.setVisibility(INVISIBLE);
    }

    private void initBox(){
        boxes = new Box[16];

        boxes[0] = new Box(redBox, true,testBox, 2, 2);
        boxes[1] = new Box(yellowBox, true,testBox, 2, 1);
        boxes[2] = new Box(blueBox1, true,testBox, 1, 2);
        boxes[3] = new Box(blueBox2, true,testBox, 1, 2);
        boxes[4] = new Box(blueBox3, true,testBox, 1, 2);
        boxes[5] = new Box(blueBox4, true,testBox, 1, 2);
        boxes[6] = new Box(greenBox1, true,testBox, 1, 1);
        boxes[7] = new Box(greenBox2, true,testBox, 1, 1);
        boxes[8] = new Box(greenBox3, true,testBox, 1, 1);
        boxes[9] = new Box(greenBox4, true,testBox, 1, 1);

        boxes[10] = new Box(wallTop, false,testBox, 5, 0.5f);
        boxes[11] = new Box(wallBottom1, false,testBox, 1.5f, 0.5f);
        boxes[14] = new Box(wallBottom2, false,testBox, 1.5f, 0.5f);
        boxes[15] = new Box(wallBottom3, false, testBox, 2, 0.5f);
        boxes[12] = new Box(wallRight, false,testBox, 0.5f, 5);
        boxes[13] = new Box(wallLeft, false,testBox, 0.5f, 5);

    }

    private void initPosition(){
        setUnitPosition(boxes[0], 1, 0); //redBox

        setUnitPosition(boxes[1], 1, 2); //yellowBox

        setUnitPosition(boxes[2], 0, 0); //blueBox1
        setUnitPosition(boxes[3], 3, 0); //blueBox2
        setUnitPosition(boxes[4], 0, 2); //blueBox3
        setUnitPosition(boxes[5], 3, 2); //blueBox4

        setUnitPosition(boxes[6], 0, 4); //greenBox1
        setUnitPosition(boxes[7], 1, 3); //greenBox2
        setUnitPosition(boxes[8], 2, 3); //greenBox3
        setUnitPosition(boxes[9], 3, 4); //greenBox4

        setUnitPosition(boxes[10], -0.5f, -0.5f); //wallTop
        setUnitPosition(boxes[11], -0.5f, 5f); //wallBottom1
        setUnitPosition(boxes[14], 3, 5f); //wallBottom2
        setUnitPosition(boxes[15], 1, 5);
        setUnitPosition(boxes[12], 4f, 0); //wallRight
        setUnitPosition(boxes[13], -0.5f, 0); //wallLeft

        synchronizePos();

    }

    //set position in center Unit
    private void setUnitPosition(Box box, float xUnit, float yUnit){
        ImageView image = box.theBox;

        float posX = unit * xUnit + mainX;
        float posY = unit * yUnit + mainY;
        image.setX(posX);
        image.setY(posY);
        //System.out.println("box ---> "  + image.getX() + " || " + image.getY());
    }


    //-------------------------------------------------------------
    //touch down
    private void touchDown(float x, float y){
        System.out.println("ACTION_DOWN---> "+ x + " || "+ y);
        detectClickedBox(x, y);
        System.out.println("selectedBox = " + selectedBox);
    }

    private void detectClickedBox(float x, float y){
        int i;
        for(i = 0; i < boxes.length; i++){
            if(boxes[i].isClicked(x, y)){
                selectedBox = i;
                if(boxes[selectedBox].isNotWall)
                    testBox.setVisibility(VISIBLE);
                boxes[selectedBox].initMove(x, y);
                break;
            }
        }
    }


    //-------------------------------------------------------------
    //touch touchMove
    private void touchMove(float x, float y){
        //System.out.println("ACTION_MOVE"+ x + "||"+ y);
        if(selectedBox != -1)
            boxes[selectedBox].move(x, y);

    }


    //---------------------------------
    //touch up

    private void touchUp(float x, float y){
        System.out.println("ACTION_UP"+ x + "||"+ y);
        unClicked();
    }

    private void unClicked(){

        //selectedBox != -1 && isMoveAllowed()
        if(selectedBox != -1 && isMoveAllowed()){
            boxes[selectedBox].endMove();
        }
        selectedBox = -1;
        testBox.setVisibility(INVISIBLE);
    }

    private boolean isMoveAllowed(){
        int i;

        float[] x1 = new float[2];
        float[] y1 = new float[2];
        float[] x2 = new float[2];
        float[] y2 = new float[2];
        x1[0] = testBox.getX();
        x1[1] = testBox.getX() + testBox.getWidth();
        y1[0] = testBox.getY();
        y1[1] = testBox.getY() + testBox.getHeight();
        for(i = 0; i < boxes.length; i++){
            if(i != selectedBox){
                x2[0] = boxes[i].x[0];
                x2[1] = boxes[i].x[1];
                y2[0] = boxes[i].y[0];
                y2[1] = boxes[i].y[1];
                if(MatrixBox.isIntersect(x1, y1, x2, y2)){
                    System.out.println(i);
                    return false;
                }
                //System.out.println(i + "-->:" + x1[0] + "||" + x1[1] + "||" + y1[0]+ "||"  + y1[1]);
                //System.out.println(i + "-->:" + x2[0] + "||" + x2[1] + "||" + y2[0]+ "||"  + y2[1]);
            }
        }
        if(!ifTestBoxInside()){
            movesCount++;
            changeText();
            return true;
        }

        return false;
    }

    private boolean ifTestBoxInside(){
        float testX[] = new float[2];
        float testY[] = new float[2];

        testX[0] = testBox.getX();
        testX[1] = testX[0] + testBox.getWidth();
        testY[0] = testBox.getY();
        testY[1] = testY[0] + testBox.getHeight();

        Box b = boxes[selectedBox];

        if(b.x[0] < testX[0] && b.x[1] > testX[1]){
            return (b.y[0] < testY[0] && b.y[1] > testY[1]);
        }
        return false;
    }


    //-----------------------//////////////////////
    //touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchUp(x, y);
                break;
        }

        return false;
    }



    //-------------------------------------------------
    //buttons

    private void restartGameClick(){
        movesCount = 0;
        changeText();
        initPosition();
        synchronizePos();
    }

    private void saveGameClick(){
        savePositions(false);
    }

    private void loadBtnClick(){
        oldPositions = SaveManagemnt.loadList(this, S_Positions_LIST);
        if(oldPositions != null){
            loadPositions();
            movesCount = SaveManagemnt.loadMoves(this, S_MOVES);
            changeText();
        }
    }

    private void savePositions(boolean auto){
        int i;

        oldPositions = new float[16][2];
        for(i = 0; i < boxes.length; i++){
            oldPositions[i][0] = boxes[i].theBox.getX();
            oldPositions[i][1] = boxes[i].theBox.getY();
        }
        if(!auto){
            SaveManagemnt.saveMoves(this, movesCount, S_MOVES);
            SaveManagemnt.saveList(this, oldPositions, S_Positions_LIST);
        }else{
            SaveManagemnt.saveMoves(this, movesCount, S_MOVES_AUTO);
            SaveManagemnt.saveList(this, oldPositions, S_Positions_LIST_AUTO);
        }
    }

    private void loadPositions(){
        int i;

        for(i = 0; i < boxes.length; i++){
            boxes[i].theBox.setX(oldPositions[i][0]);
            boxes[i].theBox.setY(oldPositions[i][1]);
        }
        synchronizePos();
    }

    private void changeText(){
        movesText.setText(label + movesCount);
    }

    private void synchronizePos(){
        int i;

        for(i = 0; i < boxes.length; i++){
            boxes[i].synchronizePos();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
                saveGameClick();
                break;
            case R.id.restartGameBtn :
                restartGameClick();
                break;
            case R.id.loadBtn:
                loadBtnClick();
                break;
        }
    }

    @Override
    protected void onStop() {
        System.out.println("saved in onDestroy");
        savePositions(true);
        super.onStop();
    }
}
