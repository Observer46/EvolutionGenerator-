package agh.cs.project.lab8.Classes;

import java.util.Random;

public class EvolvingAnimal extends AbstractWorldMapElement {
    private Genotype genotype;
    private ModuloMap map;
    private int energy=OptionParser.startEnergy;
    private MapDirection orientation;

    public EvolvingAnimal (ModuloMap map, Vector2d position){
        this.map=map;
        this.genotype= new Genotype();
        super.position=position;
        int direction=new Random().nextInt(8);
        this.orientation=MapDirection.convertToMapDir(direction);
    }

    public void move(){
        MoveDirection direction=this.genotype.getMove();
        this.orientation.rotateOrientation(direction);
        Vector2d newPosition = super.position.add(this.orientation.dirToMove());
        this.map.verifyMove(newPosition);
        super.position=newPosition;
        this.updateObservers();
    }




}
