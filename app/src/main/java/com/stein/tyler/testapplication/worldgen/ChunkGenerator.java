package com.stein.tyler.testapplication.worldgen;

import com.stein.tyler.testapplication.Pawn;
import com.stein.tyler.testapplication.math.Vector2;

import java.util.Dictionary;

/**
 * Created by Richard on 5/28/2017.
 */

public class ChunkGenerator {

    WorldChunk chunkInstance;

    public ChunkGenerator(){
       createNewChunk();
    }

    public ChunkGenerator(int newChunkWidth, int newChunkHeight){
        createNewChunk(newChunkWidth, newChunkHeight);
    }

    //Release the currently held chunk instance
    public void deleteChunk(){
        chunkInstance = null;
    }

    public void createNewChunk(){
        createNewChunk(1, 1);
    }

    public void createNewChunk(int newChunkWidth, int newChunkHeight){
        chunkInstance = new WorldChunk(newChunkWidth, newChunkHeight);
    }


    public boolean addTileContent(Vector2 pos, Pawn pawn){
        if(checkTileInBounds(pos)) { return false; }
        return ((chunkInstance.chunkMap.put(pos, pawn)) != null);
    }

    public boolean removeTileContent(Vector2 pos, Pawn pawn){
        if(checkTileInBounds(pos)) { return false; }
        return ((chunkInstance.chunkMap.remove(pos, pawn)) != null);
    }

    public boolean removeAllTileContent(Vector2 pos){
        if(checkTileInBounds(pos)) { return false; }
        return ((chunkInstance.chunkMap.removeAll(pos)) != null);
    }

    public boolean checkTileExists(Vector2 pos){
        if(checkTileInBounds(pos)) { return false; }
        return (chunkInstance.chunkMap.hasKey(pos));
    }

    public boolean checkTileInBounds(Vector2 pos){
        return (chunkInstance.size.contains(pos));
    }

    public Vector2 getChunkSize() {
        return chunkInstance.size;
    }

    public boolean setChunkSize(int newWidth, int newHeight){
        chunkInstance.size = new Vector2(newWidth, newHeight);
        return true;
    }


}
