package agh.cs.lab5;

import agh.cs.lab3.Animal;
import agh.cs.lab3.MoveDirection;
import agh.cs.project.lab8.Classes.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab7.IPositionChangeObserver;
import agh.cs.lab7.MapBoundary;
import agh.cs.project.lab8.Classes.IMapElement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, IMapElement> elements = new HashMap<>();
    protected MapBoundary boundary = new MapBoundary();

    public List<Animal> getAnimals() {
        return animals;
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement zyzio = elements.get(oldPosition);
        elements.remove(oldPosition);
        elements.put(newPosition, zyzio);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animals.add(animal);
            animal.addObserver(this);
            animal.addObserver(this.boundary);
            this.boundary.addPosition(animal.getPosition());
            elements.put(animal.getPosition(),animal);
            return true;
        }
        throw new IllegalArgumentException("Position "+ animal.getPosition().toString() +" is already occupied");
//        return false;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
 /*       for (Animal anim : animals){
            if(anim.getPosition().equals(position))
                return anim;
        }*/
        return elements.get(position);
//        return null;
    }

    @Override
    public void run(MoveDirection[] directions) {
        int i=0;
        int size = animals.size();
        for (MoveDirection dir : directions){
            Animal zyzio = animals.get(i);
//            Vector2d before = zyzio.getPosition();
            zyzio.move(dir);
//            elements.remove(before);
//            elements.put(zyzio.getPosition(),zyzio);
            i++;
            i%=size;
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

}
