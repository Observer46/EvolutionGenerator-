package agh.cs.project.lab8.Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMapElement implements IMapElement, IPositionChangeObserver{
    protected Vector2d position;
    protected List<IPositionChangeObserver> observers = new ArrayList<>();

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public int getVector2dCordX() {
        return this.position.x;
    }
    public int getVector2dCordY() {
        return this.position.y;
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
