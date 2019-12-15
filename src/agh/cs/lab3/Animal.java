package agh.cs.lab3;

import agh.cs.lab4.IWorldMap;
import agh.cs.lab5.AbstractWorldMapElement;
import agh.cs.lab7.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Animal extends AbstractWorldMapElement {
    private  MapDirection orientation = MapDirection.NORTH;
    private IWorldMap map;
    private  List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map){
        this.map=map;
        super.position=new Vector2d(2,2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this(map);
        this.position=initialPosition;
    }

    public MapDirection getOrientation(){
        return this.orientation;
    }

    public String toString(){
        String res="";
        switch (this.orientation){
            case NORTH:
                res="N";
                break;
            case EAST:
                res="E";
                break;
            case WEST:
                res="W";
                break;
            case SOUTH:
                res="S";
        }
        return res;
    }

    public void move(MoveDirection direction){
        switch (direction){
            case LEFT:
                this.orientation=this.orientation.previous();
                break;
            case RIGHT:
                this.orientation=this.orientation.next();
                break;
            case FORWARD:
                Vector2d tmp1=this.position.add(this.orientation.toUnitVector());
                if(this.map.canMoveTo(tmp1)) {
                    Vector2d oldPosition=this.position;
                    Vector2d newPosition=tmp1;
                    this.position = tmp1;
                    this.positionChanged(oldPosition,newPosition);
                }

                break;
            case BACKWARD:
                Vector2d tmp2=this.position.subtract(this.orientation.toUnitVector());
                if(this.map.canMoveTo(tmp2)) {
                    Vector2d oldPosition=this.position;
                    Vector2d newPosition=tmp2;
                    this.position = tmp2;
                    this.positionChanged(oldPosition,newPosition);
                }
                break;
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver obs : this.observers)
            obs.positionChanged(oldPosition,newPosition);
    }
}
