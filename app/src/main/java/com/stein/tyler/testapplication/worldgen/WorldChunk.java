package com.stein.tyler.testapplication.worldgen;

import com.stein.tyler.testapplication.ListMap;
import com.stein.tyler.testapplication.Pawn;
import com.stein.tyler.testapplication.math.Vector2;

/**
 * Created by Richard on 5/17/2017.
 */

public class WorldChunk {
    public WorldChunk(){
        size = new Vector2(1, 1);
        offset = new Vector2(0, 0);
        rotation = 0;
    }

    public WorldChunk(int chunkWidth, int chunkHeight){
        size = new Vector2(chunkWidth, chunkHeight);
        offset = new Vector2(0, 0);
        rotation = 0;
    }

    //Check if the chunk map contains a non-null ListMap
    public boolean hasMap(){
        return (chunkMap != null);
    }

    ListMap<Vector2, Pawn> chunkMap; //Contents of this world chunk

    Vector2 size;

    Vector2 offset; //Offset from world zero when used in a map

    float rotation; //Rotation from world 0 (0-360)



}
