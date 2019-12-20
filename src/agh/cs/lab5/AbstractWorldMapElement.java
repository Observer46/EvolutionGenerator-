package agh.cs.lab5;

import agh.cs.project.lab8.Classes.Vector2d;
import agh.cs.project.lab8.Classes.IMapElement;

public abstract class AbstractWorldMapElement implements IMapElement {
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
