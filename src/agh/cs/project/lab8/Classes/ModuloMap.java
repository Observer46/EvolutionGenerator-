package agh.cs.project.lab8.Classes;


import agh.cs.lab3.Vector2d;
import agh.cs.lab5.IMapElement;


import java.util.ArrayList;
import java.util.List;

public class ModuloMap{
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private List<EvolvingAnimal> animals = new ArrayList<>();
    private List<IMapElement>  elements = new ArrayList<>();

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





    public void place(EvolvingAnimal animal) {
        animals.add(animal);
            //animal.addObserver(this);
            //animal.addObserver(this.boundary);
            //this.boundary.addPosition(animal.getPosition());
            //elements.put(animal.getPosition(),animal);

}

    public Object objectAt(Vector2d position) {
        for (EvolvingAnimal anim : animals) {
            if (anim.getPosition().equals(position))
                return anim;
        }
        return null;
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position)!=null;
    }

    public void moveAnimals() {

    }


    public String toString(){
        return "";
    }
}
