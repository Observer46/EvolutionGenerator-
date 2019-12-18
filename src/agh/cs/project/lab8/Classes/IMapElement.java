package agh.cs.project.lab8.Classes;

public interface IMapElement {
    Vector2d getPosition(); /** Returns position of the element  */
//    int getVector2dCordX();
//    int getVector2dCordY();
//    void addObserver(IPositionChangeObserver observer);
//    void removeObserver(IPositionChangeObserver observer);
//    void gotRemoved();
    boolean isPlant();
//    String toString(); /** Returns a 1-letter symbol string representing element on the map */
}
