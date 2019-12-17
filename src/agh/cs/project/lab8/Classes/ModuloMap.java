package agh.cs.project.lab8.Classes;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
//import org.infinispan.multimap.api.embedded.*;


import java.util.ArrayList;
import java.util.List;

public class ModuloMap{
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private List<EvolvingAnimal> animals = new ArrayList<>();
    private ListMultimap<Vector2d,IMapElement> elements = ArrayListMultimap.create();

    public ModuloMap(Vector2d lowerLeft, Vector2d upperRight){
        this.lowerLeft=lowerLeft;
        this.upperRight=upperRight;
    }

    public Vector2d verifyMove(Vector2d position) {
        Vector2d verifiedPosition=position;
        if(!position.precedes(upperRight)){
            verifiedPosition=new Vector2d(position.x%upperRight.x,position.y%upperRight.y);
        }
        if(!position.follows(lowerLeft)){
            verifiedPosition=new Vector2d(position.x%lowerLeft.x,position.y%lowerLeft.y);
        }
        return verifiedPosition;
    }





    public void place(IMapElement element) {
        this.elements.put(element.getPosition(),element);
        if (element instanceof EvolvingAnimal) {
            EvolvingAnimal animal = (EvolvingAnimal) element;
            animals.add(animal);
        }
    }

    public void remove(IMapElement element) {
        this.elements.remove(element.getPosition(),element);
        if (element instanceof EvolvingAnimal){
            EvolvingAnimal animal = (EvolvingAnimal) element;
            animals.remove(animal);
        }
    }

    public Object objectsAt(Vector2d position) {
        for (EvolvingAnimal anim : animals) {
            if (anim.getPosition().equals(position))
                return anim;
        }
        return null;
    }

    public boolean isOccupied(Vector2d position){
        return objectsAt(position)!=null;
    }

    public void moveAnimals() {

    }

//    @Override
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
//        IMapElement zyzio = elements.get(oldPosition);
//        elements.remove(oldPosition);
//        elements.put(newPosition, zyzio);
//    }


    public String toString(){
        return "";
    }
}
