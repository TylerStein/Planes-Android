package com.stein.tyler.testapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Richard on 5/14/2017.
 */



public class ListMap <Key, Val>{
    //3D Array for (X,Y) and stacked tile contents
    //private ArrayList<T> contents[][];
    private Map<Key, List<Val>> map = new HashMap<Key, List<Val>>();

    int width, height;

    public ListMap(){
        //contents = new ArrayList[width][width];
        map = new HashMap<Key, List<Val>>();
        clear();
    }

    public ListMap(int width, int height){
        this.width = width;
        this.height = height;
        //contents = new ArrayList[width][height];
        map = new HashMap<Key, List<Val>>();
        clear();
    }

    //Safe retrieval, returns null on failure
    public List<Val> getAll(Key key){
        return map.get(key);
    }

    //Returns values within index range at key, or null on failure
    public List<Val> getRange(Key key, int begin, int end){
        if(map.containsKey(key)){
            return map.get(key).subList(begin, end);
        }
        return null;
    }

    //Unsafe retrieval, throws IndexOutOfBoundsException on failure
    public Val get(Key key, int idx){
        return map.get(key).get(idx);
    }

    //Safe retrieval, returns null on failure
    public Val get_safe(Key key, int idx){
        Val res = null;
        try{
            res = map.get(key).get(idx);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Exception! e: " + e.getMessage());
        }
        return res;
    }

    //Adds an object to a location on the grid, returns a reference to the added object
    public Val put(Key key, Val val){
        if(map.containsKey(key)){
            map.get(key).add(val);
        }else{
            map.put(key, new ArrayList<Val>());
            map.get(key).add(val);
        }
        return val;
    }

    //Adds a collection to a location on the grid, returns a reference to the updated list
    public List<Val> putAll(Key key, Collection<Val> val){
        if(!map.containsKey(key)){
            map.put(key, new ArrayList<Val>());
        }

        //Add to the list (lists copy by reference)
        List<Val> listRef = map.get(key);
        for(Val v : val){
            listRef.add(v);
        }

        return listRef;
    }

    //Takes a value by reference, removes it from old spot and adds to new spot, returns false on failure
    public boolean move(Val val, Key srcKey, Key dstKey){
        if(!map.containsKey(srcKey)){
            //The key doesn't exist!
            System.out.println("Attempting to move nonexistant object (key not found)!");
            return false;
        }

        //Remove the value from the old key
        getAll(srcKey).remove(val);

        //Add the new key and value
        put(dstKey, val);

        return true;
    }

    //Moves/appends all contents of one key to another key, returns false on failure
    public boolean moveAll(Key srcKey, Key dstKey){
        if(!map.containsKey(srcKey)){
            //The key doesn't exist!
            System.out.println("Attempting to move objects on a nonexistant key!");
            return false;
        }

        if(!map.containsKey(dstKey)){
            map.put(dstKey, new ArrayList<Val>()).addAll(map.get(srcKey));
            map.remove(srcKey);
        }else{
            map.get(dstKey).addAll(map.get(srcKey));
            map.remove(srcKey);
        }

        return true;
    }

    //Moves/appends a range of content from one key to another key
    public boolean moveRange(Key srcKey, Key dstKey, int begin, int end){
        if(!map.containsKey(srcKey)){
            //The key doesn't exist!
            System.out.println("Attempting to move objects on a nonexistant key!");
            return false;
        }

        //Create a new list
        List<Val> subList = map.get(srcKey).subList(begin, end);

        if(!map.containsKey(dstKey)){
            //Key doesn't exist, create with sublist
            map.put(dstKey, subList);
        }else{
            //Key axists, append sublist contents and null sublist
            map.get(dstKey).addAll(subList);
            subList = null;
        }

        return true;
    }

    //Safe removal by reference(returns null if nonexistant)
    public Val remove(Key key, Val val){
        if(map.containsKey(key)){
            List<Val> listRef = map.get(key);
            if(listRef.contains(val)){
                listRef.remove(val);
                return val;
            }
        }
        return null;
    }

    //Safe removal by index(returns null if nonexistant)
    public Val remove(Key key, int idx){
        if(map.containsKey(key)){
            List<Val> listRef = map.get(key);
            if(idx < listRef.size()){
                Val resVal = listRef.get(idx);
                listRef.remove(idx);
                return resVal;
            }
        }
        return null;
    }

    //Safe removal of all of a key's entries (deletes the key)(returns null if nonexistant)
    public List<Val> removeAll(Key key){
        List<Val> resList = null;
        if(map.containsKey(key)){
            resList = map.get(key);
            map.remove(key);
        }

        return resList;
    }

    public boolean isKeyEmpty(Key key){
        return map.get(key).isEmpty();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public Map<Key, List<Val>> getMap(){
        return map;
    }

    //Checks if map contains given key
    public boolean hasKey(Key key){
        return map.containsKey(key);
    }

    //Checks if map contains the given key and value pair
    public boolean hasValue(Key key, Val value) {
        if(map.containsKey(key)){
            return map.get(key).contains(value);
        }
        return false;
    }

    //Expensive search for a specific value reference
    public boolean hasValue(Val value){
        for(List<Val> mapIter : map.values()){
            for(Val listIter : mapIter){
                if(listIter == value){
                    return true;
                }
            }
        }
        return false;
    }

    //Expensive search for all uses of this value reference
    public List<Key> getAllKeysContaining(Val value){
        List<Key> resList = new ArrayList<Key>();

        //Iterate over each entry
        for(Entry<Key, List<Val>> entryIter : map.entrySet()){
            for(Val valIter : entryIter.getValue()){
                if(valIter == value){
                    resList.add(entryIter.getKey());
                }
            }
        }


        return resList;
    }

    //Clear/reset the map and its content references
    public void clear(){
        //Clear all lists
        for(List<Val> listIter : map.values()){
            listIter.clear();
        }

        //Clear the map
        map.clear();
    }

    //toString print/debug function
    public String toString(){
        String res = "";

        res += "\n---MAP PRINT---\n";

        //Iterate over each entry
        if(map != null) {
            for (Entry<Key, List<Val>> entryIter : map.entrySet()) {
                res += "KEY["  + entryIter.getKey().toString() + "]\n";
                for (Val valIter : entryIter.getValue()) {
                    res += "\t\t\tVALUE["  + entryIter.getKey().toString() + "]\n";
                }
            }
        }else{
            res += "No map data found!\n";
        }

        res += "\n---END PRINT--\n";

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListMap<?, ?> listMap = (ListMap<?, ?>) o;

        if (width != listMap.width) return false;
        if (height != listMap.height) return false;
        return map.equals(listMap.map);

    }

    @Override
    public int hashCode() {
        int result = map.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }


    //Safe retreival (out of bounds catch), returns null
    /*public ArrayList<T> get(int x, int y){
        ArrayList<T> resList;
        try{
            resList = contents[x][y];
        }catch(ArrayIndexOutOfBoundsException e){
            resList = null;
        }
        return resList;
    }

    //Get but check for valid XY, returns null
    public ArrayList<T> get2(int x, int y){
        if(checkValid(x, y)){
            return get_unsafe(x, y);
        }
        return null;
    }

    //Unsafe retreival
    public  ArrayList<T> get_unsafe(int x, int y){
        return contents[x][y];
    }

    //Safe adding to a space (out of bounds catch)
    public void add(int x, int y, T what){
        try{
            contents[x][y].add(what);
        }catch(ArrayIndexOutOfBoundsException e){
            return;
        }
    }

    //Add to a space's list (unsafe)
    public void add_unsafe(int x, int y, T what){
        contents[x][y].add(what);
    }

    //Safe remove
    public void remove(int x, int y, T what){
        if(checkValid(x, y)) {
          remove_unsafe(x, y, what);
        }
    }

    //Unsafe remove
    public void remove_unsafe(int x, int y, T what){
        for (int i = 0; i < contents[x][y].size(); i++) {
            if (contents[x][y].get(i) == what) {
                contents[x][y].remove(i);
                return;
            }
        }
    }


    //Safe find
    public T find(int x, int y, T what){
        if(checkValid(x, y)) {
           return find_unsafe(x, y, what);
        }

        return null;
    }

    //Unsafe find
    public T find_unsafe(int x, int y, T what){
        for (int i = 0; i < contents[x][y].size(); i++) {
            if (contents[x][y].get(i) == what) {
                return contents[x][y].get(i);
            }
        }

        return null;
    }


    //Reset the grid size and contents
    public void reset(int width, int height){
        contents = new ArrayList[width][height];
        // clear();
        this.width = width;
        this.height = height;
    }

    //Resize the grid while preserving untouched locations
    public void crop(int width, int height){
        //Create a temporary grid to act as the new resized one
        ArrayList<T> tmpList[][] = new ArrayList[width][height];
        //Iterate through the grid
        for(int x = 0; x < width; x++){
            for(int y = 0; y < width; y++){
                //Check if this location is valid on the old grid
                if(checkValid(x, y)){
                    //Copy the contents of the old grid at this location
                    tmpList[x][y] = contents[x][y];
                }else{
                    //Create an empty grid slot
                    tmpList[x][y] = new ArrayList();
                }
            }
        }

        //Set the new width, height, and content array
        this.width = width;
        this.height = height;
        contents = tmpList;
    }

    //Check if a location is within the grid
    private boolean checkValid(int x, int y){
        return(x > 0 && x < width && y > 0 && y < height);
    }

    private void clear(){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                contents[x][y] = new ArrayList();
            }
        }
    }

    */
}
