package agh.cs.project.lab8.Classes;


import agh.cs.lab3.*;
import agh.cs.lab5.AbstractWorldMapElement;
import agh.cs.lab7.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.List;

public class EvolvingAnimal extends AbstractWorldMapElement {
    private Genotype genotype;
    private ModuloMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int energy=20;
    private int orientation=0;

    public EvolvingAnimal (ModuloMap map, Vector2d position){
        this.map=map;
        super.position=position;
    }

    public void move(){
        int direction=this.genotype.getMove();
        int newDirection = (this.orientation + direction)%8;
        Vector2d newPosition = super.position.add(Vector2d.toUnitVec(newDirection));
        this.map.verifyMove(newPosition);
        super.position=newPosition;
        //this.updateObservers();


    }


}
