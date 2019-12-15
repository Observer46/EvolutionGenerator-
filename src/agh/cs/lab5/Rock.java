package agh.cs.lab5;

import agh.cs.lab3.Vector2d;

public class Rock extends AbstractWorldMapElement{

    public Rock(Vector2d position){
        super.position=position;
    }

    public String toString(){
        return "r";
    }
}
