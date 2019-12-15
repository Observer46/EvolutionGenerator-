package agh.cs.lab5;

import agh.cs.lab3.Vector2d;

public abstract class AbstractWorldMapElement implements IMapElement{
    protected Vector2d position;

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
}
