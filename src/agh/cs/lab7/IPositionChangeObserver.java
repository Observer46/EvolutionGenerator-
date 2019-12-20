package agh.cs.lab7;

import agh.cs.project.lab8.Classes.Vector2d;

public interface IPositionChangeObserver {
    void  positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
