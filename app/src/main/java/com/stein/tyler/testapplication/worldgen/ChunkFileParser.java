package com.stein.tyler.testapplication.worldgen;

/**
 * Created by Richard on 5/28/2017.
 */

import com.stein.tyler.testapplication.ListMap;
import com.stein.tyler.testapplication.Pawn;
import com.stein.tyler.testapplication.math.Vector2;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class ChunkFileParser {


    public static ListMap<Vector2, Pawn> parseChunkFile(String filePath)
        throws XmlPullParserException, IOException {

        if(!validateFilePath(filePath)) {return null;}

        ListMap<Vector2, Pawn> resMap = new ListMap<>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader("<foo>Hello world!</foo>"));

        int eventType = xpp.getEventType();

        while(eventType !=  XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
               if(xpp.getText() == "tile"){
                   Vector2 tilePos = new Vector2(0, 0);
                   List<Pawn> pawnsOnThisTile = new ArrayList<>();

                   for(int i = 0; i < xpp.getAttributeCount(); i++){
                       String atr = xpp.getAttributeName(i);
                       String val = xpp.getAttributeValue(i);

                       if (atr == "x"){
                            tilePos.set((int)Integer.valueOf(val), tilePos.getY());
                       }

                       if(atr == "y"){
                            tilePos.set(tilePos.getX(), (int)Integer.valueOf(val));
                       }
                   }



               }
            }

            eventType = xpp.next();
        }


        return resMap;
    }

    public static boolean validateFilePath(String filePath){
        File checkFile = new File(filePath);
        if(!checkFile.isDirectory()){
            return false;
        }
        if(!checkFile.exists()){
            return false;
        }
        if(!checkFile.canRead()){
            return false;
        }
        return true;
    }


    public static String getChunkFilesDir(){
        return "";
    }
}
