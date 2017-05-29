package com.stein.tyler.testapplication;

import android.graphics.Color;

import java.util.Dictionary;
import com.stein.tyler.testapplication.math.Vector2;

/**
 * Created by Richard on 5/14/2017.
 */

public class Pawn
{
    Vector2 position;   //(X,Y) position
    float orientation;  //Up-Right relative rotation (0.0 to 360.0)
    String name;        //Non-unique name
    int identifier = Color.BLACK;  //Pawn representative color
    boolean isStatic = false;

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }



    Dictionary<String, String> properties; //String properties dictionary

    public Pawn(){
        this.name = "";
        this.position = new Vector2();
        this.orientation = 0;
    }

    public Pawn(String name){
        this.name = name;
        this.position = new Vector2();
        this.orientation = 0;
    }

    public Pawn(String name, Vector2 position){
        this.name = name;
        this.position = position;
        this.orientation = 0;
    }

    public Pawn(String name, Vector2 position, float orientation){
        this.name = name;
        this.position = position;
        this.orientation = orientation;
    }

    public Pawn Copy(){
        Pawn p = new Pawn(name);
        p.position = position;
        p.orientation = orientation;
        p.identifier = identifier;
        p.isStatic = isStatic;
        return p;
    }

    public String toString() {
        return "Pawn (" + name + ") @ " + position.toString() + " ang(" + orientation + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pawn pawn = (Pawn) o;

        if (Float.compare(pawn.orientation, orientation) != 0) return false;
        if (identifier != pawn.identifier) return false;
        if (!position.equals(pawn.position)) return false;
        if (!name.equals(pawn.name)) return false;
        return properties != null ? properties.equals(pawn.properties) : pawn.properties == null;

    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + (orientation != +0.0f ? Float.floatToIntBits(orientation) : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) identifier;
        return result;
    }


}
