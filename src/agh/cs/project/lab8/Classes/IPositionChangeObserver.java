package agh.cs.project.lab8.Classes;

public interface IPositionChangeObserver {
    void  positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
