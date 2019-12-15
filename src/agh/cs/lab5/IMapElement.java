package agh.cs.lab5;

import agh.cs.lab3.Vector2d;

public interface IMapElement {
    Vector2d getPosition(); /** Returns position of the element  */
    int getVector2dCordX();
    int getVector2dCordY();

    String toString(); /** Returns a 1-letter symbol string representing element on the map */
}
