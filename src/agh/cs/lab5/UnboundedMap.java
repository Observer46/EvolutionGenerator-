package agh.cs.lab5;

import agh.cs.project.lab8.Classes.Vector2d;
import agh.cs.lab4.MapVisualizer;
import agh.cs.project.lab8.Classes.IMapElement;

import java.util.List;

public class UnboundedMap extends AbstractWorldMap {
    private List<Rock> rocks;

    public UnboundedMap(List<Rock> rocks) {
        this.rocks = rocks;
        for (IMapElement rock : rocks){
            super.elements.put(rock.getPosition(), rock);
            super.boundary.addPosition(rock.getPosition());
        }

    }

//    @Override
//    public Object objectAt(Vector2d position) {
//        Object object = super.objectAt(position);
//        for(Rock rock : rocks){
//            if(rock.getPosition().equals(position))
//                object=rock;
//        }
//        return object;
//    }

    public boolean placeRock(Rock rock){
        if (canMoveTo(rock.getPosition())){
            rocks.add(rock);
            super.boundary.addPosition(rock.getPosition());
            return true;
        }
        return false;
    }

    public String toString(){

        if(super.elements.size()==0)    return "";
        Vector2d lowLeft=super.boundary.getLowerLeft();
        Vector2d highRight=super.boundary.getUpperRight();

        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowLeft,highRight);
    }
}
