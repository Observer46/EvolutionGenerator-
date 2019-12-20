package agh.cs.project.lab8.Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMapElement implements IMapElement{
    protected Vector2d position;
    protected ModuloMap map;

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

//    @Override
//    public int getVector2dCordX() {
//        return this.position.x;
//    }
//    public int getVector2dCordY() {
//        return this.position.y;
//    }



//    public void gotRemoved(){
//        this.map.remove(this);
//        for(IPositionChangeObserver obs : observers)    removeObserver(obs);
//    }
}
