package agh.cs.project.lab8.Classes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OptionParser {
    static public int mapWidth;
    static public int mapHeight;
    static public int startEnergy;
    static public int startAnimalNumber;
    static public int moveEnergy;
    static public int plantEnergy;
    static public int jungleRatioWidth; // Percentage
    static public int jungleRatioHeight; // Percentage

    public  OptionParser() throws FileNotFoundException, IOException,  ParseException{
        JSONObject jsonParser =(JSONObject) new JSONParser().parse(new FileReader("properties.json"));
        this.mapWidth =(int) (long) jsonParser.get("width");
        if(this.mapWidth <=0)   throw new IllegalArgumentException("Map's width must be a positive integer!");

        this.mapHeight =(int) (long) jsonParser.get("height");
        if(this.mapHeight <=0)   throw new IllegalArgumentException("Map's height must be a positive integer!");

        this.startEnergy = (int) (long) jsonParser.get("startEnergy");
        if(this.startEnergy <=0)   throw new IllegalArgumentException("Starting energy must be a positive integer!");

        this.startAnimalNumber = (int) (long) jsonParser.get("startAnimalNumber");
        if(this.startAnimalNumber<=0)    throw new IllegalArgumentException("Animal count on start must be a positive integer!");

        this.moveEnergy = (int) (long) jsonParser.get("moveEnergy");
        if(this.moveEnergy<0 || this.moveEnergy>= this.startEnergy)     throw new IllegalArgumentException("Move energy must be in range 0-maxEnergy!");

        this.plantEnergy = (int) (long) jsonParser.get("plantEnergy");
        if(this.plantEnergy<0) throw new IllegalArgumentException("Plant's energy boost must be a positive integer!");

        JSONObject jungleRation = (JSONObject) jsonParser.get("jungleRatio");
        this.jungleRatioWidth = (int) (long) jungleRation.get("width");
        if(this.jungleRatioWidth<0 || this.jungleRatioWidth>100)    throw new IllegalArgumentException("Jungle width ration is a per cent - from 0 to 100");

        this.jungleRatioHeight = (int) (long) jungleRation.get("height");
        if(this.jungleRatioHeight<0 || this.jungleRatioHeight>100)    throw new IllegalArgumentException("Jungle height ration is a per cent - from 0 to 100");

    }
}
