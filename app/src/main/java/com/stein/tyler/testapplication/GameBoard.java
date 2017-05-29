package com.stein.tyler.testapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.stein.tyler.testapplication.math.Vector2;

/**
 * Created by Richard on 5/14/2017.
 */

public class GameBoard {

    //2D Grid containing lists of pawns
    public ListMap<Vector2, Pawn> grid;

    //Pawn ID manager
    public IDManager idManager;

    //Grid size
    private int width, height;
    final int defaultSize = 12;

    public GameBoard(){
        grid = new ListMap<Vector2, Pawn>();
        idManager = new IDManager();
        width = defaultSize;
        height = defaultSize;
        resetBoard();
    }

    public GameBoard(int width, int height){
        grid = new ListMap<Vector2, Pawn>();
        idManager = new IDManager();
        this.width = width;
        this.height = height;
        resetBoard();
    }

    //Add a pawn
    public Pawn addPawn(Vector2 where, Pawn what){
        what.position = where;
        return grid.put(where, what);
    }

    //Remove a pawn
    public boolean removePawn(Vector2 where, Pawn what){
        what.position = new Vector2();
        return !(grid.remove(where, what) == null);
    }

    //Move a pawn
    public boolean movePawn(Pawn who, Vector2 whereTo){
        boolean status = grid.move(who, who.position, whereTo);
        if(status){who.position = whereTo;}
        return status;
    }

    //Get pawns by name from a specific spot (names not unique)
    public Pawn findPawn(Vector2 where, int pawnIndex) {
        return grid.get(where, pawnIndex);
    }

    //Get all pawns on a spot
    public List<Pawn> getPawns(Vector2 where){
        return grid.getAll(where);
    }

    //Get pawns by name from a specific spot (names not unique)
    public List<Pawn> getPawns(Vector2 where, String name) {
        List<Pawn> resList = grid.getAll(where);
        if(resList != null){
            for(int i = resList.size(); i > 0; i--){
                if(resList.get(i).name != name){
                    resList.remove(i);
                }
            }
        }

        return resList;
    }

    //Check if anything exists on this spot
    public boolean checkContents(Vector2 where){
        return grid.hasKey(where);
    }

    public String toString() {
        return "(GameBoard:Grid)" + grid.toString();
    }

    public void resetBoard(){
        grid.clear();
        idManager.clearIDs();

    }

    public void resetBoard(int newWidth, int newHeight){
        width = newWidth;
        height = newHeight;
        resetBoard();
    }

    public void cropBoard(int newWidth, int newHeight){
        System.out.println("Cropping board request denied.. functionality not complete!\n");
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

//Helper class for object IDs
class IDManager{
    final int InvalidIndex = -1;
    final int MaxIDs = 1024;
    public int ID[];
    private int nextID = InvalidIndex;

    public IDManager(){
        ID = new int[MaxIDs];
        nextID = 0;
        clear();
    }


    //Checks if this index is within bounds and valid
    public boolean checkValid(int index) {
        return (index < MaxIDs && index > InvalidIndex);
    }

    public int getValue(int index){
        if(checkValid(index)){
            return ID[index];
        }
        return InvalidIndex;
    }

    //Returns the next free ID slot and counts this ID as taken
    public int getNextAvailibleID(){
        int res = InvalidIndex;
        if(checkValid(nextID)){
            res = nextID;
            setValue(nextID, res);
            nextID = findNextAvailibleID();
        }
        return res;
    }

    //Reuses ID slots by prioritising previously used slots
    public int recycleID(int index){
        if(!checkValid(index)){return findNextAvailibleID();}
        ID[index] = InvalidIndex;
        setNextAvailibleID(index);
        return InvalidIndex;
    }

    public void clearIDs(){
        ID = null;
        ID = new int[MaxIDs];
        nextID = 0;
    }

    //Set the index to be used as the next availible ID
    private int setNextAvailibleID(int index){
        if(!checkValid(index)){return InvalidIndex;}
        ID[index] = index;
        return index;
    }

    //Find the next unassigned ID slot
    private int findNextAvailibleID(){
        int res = InvalidIndex;
        for(int i = 0; i < MaxIDs; i++){
            if(ID[i] == InvalidIndex){
                ID[i] = i;
                res = i;
                break;
            }
        }
        return res;
    }

    //Wipes all ID slots
    private void clear(){
        for(int i = 0; i < MaxIDs; i++){
            ID[i] = InvalidIndex;
        }
    }

    private void setValue(int index, int value){
        ID[index] = value;
    }
}

