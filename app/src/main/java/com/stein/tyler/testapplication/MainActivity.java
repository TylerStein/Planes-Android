package com.stein.tyler.testapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.stein.tyler.testapplication.math.Vector2;
import com.stein.tyler.testapplication.views.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GameBoard gameBoard;
    Pawn player;

    List<TextView> targetButtons;
    List<Button> contextButtons;
    TextView mapDisplay;

    GameView gameView;

    final int mapX = 24;
    final int mapY = 24;

    Random rnd;

    //Activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        rnd = new Random();

        //Get the layouts to fill
        final LinearLayout targetsContainer = (LinearLayout) findViewById(R.id.LinearLayout_TextOutput);
        final TableLayout contextButtonsContainer = (TableLayout) findViewById(R.id.Table_Buttons);

        //Get the map view to fill
        //mapDisplay = (TextView) findViewById(R.id.EditText_MapView);
        //mapDisplay.setText("");

        final LinearLayout mainContainer = (LinearLayout) findViewById(R.id.LinearLayout_Main);

        gameView = (GameView) findViewById(R.id.GameView);

        targetButtons = new ArrayList<TextView>();
        final int numTargets = 12;
        for(int i = 0; i < numTargets; i++) {
            final TextView newView = generateTargetText("I'm Text View number " + i + "!");
            targetsContainer.addView(newView);
            targetButtons.add(newView);
        }

        final int numButtons = 9;
        final int buttonsPerRow = 3;
        contextButtons = new ArrayList<Button>();
        TableRow thisRow = (TableRow) contextButtonsContainer.getChildAt(0);
        for(int i = 0; i < numButtons; i++){
            final Button newButton = generateContextButton("Context Button " + i);

            if(numButtons % buttonsPerRow == 0){
                thisRow = new TableRow(this);
                contextButtonsContainer.addView(thisRow);
            }

            newButton.setClickable(true);
            newButton.setOnClickListener(this);
            thisRow.addView(newButton);
            contextButtons.add(newButton);
        }

        player = new Pawn("Player");
        player.identifier = Color.GREEN;
        player.isStatic = false;

        Vector2 center = new Vector2((int)(mapX / 2), (int)(mapY / 2));
        player.position = center;

        ListMap<Vector2, Pawn> newMap = generateMap();
        gameView.updateMap(newMap);
        gameView.setMapSize(mapX, mapY);
    }

    @Override
    public void onClick(View view) {
        if(gameView != null) {
            int id = view.getId();
            if(id == R.id.btn_northwest){
                Vector2 newPos = new Vector2(-1, -1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_north){
                Vector2 newPos = new Vector2(0, -1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_northeast){
                Vector2 newPos = new Vector2(1, -1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_west){
                Vector2 newPos = new Vector2(-1, 0).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_east){
                Vector2 newPos = new Vector2(1, 0).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_southwest){
                Vector2 newPos = new Vector2(-1, 1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_south){
                Vector2 newPos = new Vector2(0, 1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_southeast){
                Vector2 newPos = new Vector2(1, 1).add(player.position);
                if(new Vector2(mapX, mapY).contains2(newPos) && newPos.getX() >= 0 && newPos.getY() >= 0) {
                    player.position = newPos;
                    gameView.updateMap(generateMap());
                }
            }else if(id == R.id.btn_here) {
                contextButtons.add(generateContextButton("Context button for location: " + player.position.toString()));
            }

            gameView.redrawCanvas();
        }
    }

    public ListMap<Vector2, Pawn> generateMap(){
        ListMap<Vector2, Pawn> resMap = new ListMap<Vector2, Pawn>();

        Pawn wallPawn = new Pawn("Wall");
        wallPawn.isStatic = true;
        wallPawn.identifier = Color.DKGRAY;

        Pawn floorPawn = new Pawn("Floor");
        floorPawn.isStatic = true;
        floorPawn.identifier = Color.LTGRAY;


        for(int x = 0; x < mapX; x++){
            for(int y = 0; y < mapY; y++){
                if(x == 0 || x >= mapX - 1|| y == 0 || y >= mapY - 1){
                    resMap.put(new Vector2(x, y), wallPawn.Copy());
                }else{
                    resMap.put(new Vector2(x, y), floorPawn.Copy());
                }
            }
        }

        resMap.put(player.position, player);

        return resMap;
    }

    public String readMap(ListMap<Vector2, Pawn> map, int mapX, int mapY, Vector2 offset){
        String res = "";
        for(int x = offset.getX() / 2; x < mapX; x++){
            for(int y = offset.getY() / 2; y < mapY; y++){
                List<Pawn> tileContents = map.getAll(new Vector2(x, y));
                if(tileContents.isEmpty() == false) {
                    res += tileContents.get(tileContents.size() - 1).identifier;
                }else{
                    res += "?";
                }
            }
            res += "\n";
        }
        return res;
    }

    public Button generateContextButton(String label){
        final Button btn = new Button(this);
        btn.setText(label);
        btn.setPadding(6, 4, 6, 4);
        return btn;
    }

    public TextView generateTargetText(String label){
        final TextView newView = new TextView(this);
        newView.setPadding(6, 0, 0, 0);
        newView.setCursorVisible(false);
        newView.setClickable(true);
        newView.setOnClickListener(this);
        newView.setText(label);
        return newView;
    }


}
