package agh.cs.lab4;
import agh.cs.lab3.Vector2d;
import agh.cs.lab5.AbstractWorldMap;
public class RectangularMap extends AbstractWorldMap {
    private Vector2d upperRight;
    private static Vector2d lowerLeft = new Vector2d(0,0);


    public RectangularMap (int width, int height) {
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.precedes(upperRight) && position.follows(lowerLeft);
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeft,upperRight);
    }
}
