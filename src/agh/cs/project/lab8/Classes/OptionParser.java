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

    public  OptionParser(){
        try {
            JSONObject jsonParser =(JSONObject) new JSONParser().parse(new FileReader("properties.json"));

            this.mapWidth =(int) (long) jsonParser.get("width");
            this.mapHeight =(int) (long) jsonParser.get("height");
            this.startEnergy = (int) (long) jsonParser.get("startEnergy");
            this.startAnimalNumber = (int) (long) jsonParser.get("startAnimalNumber");
            this.moveEnergy = (int) (long) jsonParser.get("moveEnergy");
            this.plantEnergy = (int) (long) jsonParser.get("plantEnergy");
            JSONObject jungleRation = (JSONObject) jsonParser.get("jungleRatio");
            this.jungleRatioWidth = (int) (long) jungleRation.get("width");
            this.jungleRatioHeight = (int) (long) jungleRation.get("height");
        } catch (FileNotFoundException ex1){
            System.out.println(ex1.getMessage());
            ex1.printStackTrace();
        } catch (IOException ex2){
            System.out.println(ex2.getMessage());
        } catch (ParseException ex3){
            System.out.println(ex3.getUnexpectedObject());
        }

    }
}
