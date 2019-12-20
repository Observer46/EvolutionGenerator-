package agh.cs.lab7;

import agh.cs.project.lab8.Classes.Vector2d;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
//    Comparator<IMapElement> xComparator = Comparator.comparing(IMapElement::getVector2dCordX);
//    Comparator<IMapElement> yComparator = Comparator.comparing(IMapElement::getVector2dCordY);

//    private SortedSet <IMapElement> onX= new TreeSet<>(xComparator);
//    private SortedSet <IMapElement> onY= new TreeSet<>(yComparator);
    Comparator<Vector2d> xComparator = Comparator.comparing(Vector2d::getX);
    Comparator<Vector2d> yComparator = Comparator.comparing(Vector2d::getY);
    private SortedSet <Vector2d> onX= new TreeSet<>(xComparator);
    private SortedSet <Vector2d> onY= new TreeSet<>(yComparator);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.onX.remove(oldPosition);
        this.onX.add(newPosition);

        this.onY.remove(oldPosition);
        this.onY.add(newPosition);
    }
    public Vector2d getUpperRight(){
        return new Vector2d(this.onX.last().x,this.onY.last().y);
    }
    public Vector2d getLowerLeft(){
        return new Vector2d(this.onX.first().x,this.onY.first().y);
    }
    public void addPosition(Vector2d position){
        this.onX.add(position);
        this.onY.add(position);
    }

}
