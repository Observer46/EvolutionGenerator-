package agh.cs.lab5;

import agh.cs.project.lab8.Classes.Vector2d;

public class Rock extends AbstractWorldMapElement{

    public Rock(Vector2d position){
        super.position=position;
    }

    public String toString(){
        return "r";
    }
}
