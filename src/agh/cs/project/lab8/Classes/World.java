package agh.cs.project.lab8.Classes;

import agh.cs.project.lab8.Classes.Vector2d;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        try {
            OptionParser options = new OptionParser();
            Vector2d upperRight = new Vector2d(OptionParser.mapWidth, OptionParser.mapHeight);
            ModuloMap firstMap = new ModuloMap(upperRight);
            ModuloMap secondMap = new ModuloMap(upperRight);

            firstMap.placeFirstAnimals();
            secondMap.placeFirstAnimals();
            SafariVisualization visualization = new SafariVisualization(firstMap,secondMap);
            visualization.startAnimation();
        }catch (FileNotFoundException ex1){
            System.out.println(ex1.getMessage());
            ex1.printStackTrace();
        }catch (IOException ex2){
            System.out.println(ex2.getMessage());
            ex2.printStackTrace();
        }catch (ParseException ex3){
            System.out.println(ex3.getMessage());
            ex3.printStackTrace();
        }catch (IllegalArgumentException ex4){
            System.out.println(ex4.getMessage());
            ex4.printStackTrace();
        }

    }
}
