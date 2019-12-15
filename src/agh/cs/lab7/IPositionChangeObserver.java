package agh.cs.lab7;

import agh.cs.lab3.Vector2d;

public interface IPositionChangeObserver {
    void  positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
