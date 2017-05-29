package com.stein.tyler.testapplication.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.stein.tyler.testapplication.ListMap;
import com.stein.tyler.testapplication.Pawn;
import com.stein.tyler.testapplication.math.Vector2;

import java.util.List;


/**
 * Created by Richard on 5/18/2017.
 */

public class GameView extends View {

    ListMap<Vector2, Pawn> mapRef = new ListMap<>();

    Vector2 mapSize = new Vector2(0, 0);

    Vector2 mapOffset = new Vector2(0, 0);

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.BLACK);

        final int sz = 40;

        if(mapRef != null){
            for(int x = mapOffset.getX(); x < mapSize.getX(); x++){
                for(int y = mapOffset.getY(); y < mapSize.getY(); y++){
                    List<Pawn> tileContents = mapRef.getAll(new Vector2(x, y));
                    if(tileContents.isEmpty() == false) {
                        p.setColor(tileContents.get(tileContents.size() - 1).getIdentifier());
                    }else{
                        p.setColor(Color.WHITE);
                    }
                    canvas.drawRect(x * sz, y * sz, (x * sz) + sz, (y * sz) + sz, p);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void redrawCanvas(){
        invalidate();
    }

    public void updateMap(ListMap<Vector2, Pawn> updatedMap){
        mapRef = updatedMap;
    }

    public void setMapSize(int mapX, int mapY){
        mapSize = new Vector2(mapX, mapY);
    }

    public void updateMapOffset(Vector2 offset){
        if(offset.getX() < mapSize.getX() && offset.getY() < mapSize.getY()) {
            mapOffset = offset;
            redrawCanvas();
        }
    }
}
