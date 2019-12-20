package agh.cs.project.lab8.Classes;

import agh.cs.lab3.Animal;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvolvingAnimal extends AbstractWorldMapElement {
    private Genotype genotype;
    private int energy;
    private MapDirection orientation;
    private static int minEnergyToReproduce = OptionParser.startEnergy/2;
    private static int maxEnergyLimit=OptionParser.startEnergy;
    private List<IPositionChangeObserver> observers = new ArrayList<>();



    public EvolvingAnimal (ModuloMap map, Vector2d position){
        this.map=map;
        this.genotype= new Genotype();
        super.position=position;
        int direction=new Random().nextInt(8);
        this.energy=OptionParser.startEnergy;
        this.orientation=MapDirection.convertToMapDir(direction);
    }

    public EvolvingAnimal(ModuloMap map, Vector2d position, Genotype genotype, int energy){
        this(map,position);
        this.energy=energy;
        this.genotype=genotype;
    }

    public int getEnergy(){
        return this.energy;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }

    public void move(){
        Vector2d oldPosition= super.position;
        MoveDirection direction=this.genotype.getMove();
        this.orientation.getMoveOrientation(direction); // Poprawnie ustawiamy kierunek zwięrzątka
        Vector2d newPosition = super.position.add(this.orientation.dirToMove()); // Wykonujemy ruch
        newPosition=this.map.verifyMove(newPosition);       // Weryfikujemy pozycję
        super.position=newPosition;
        this.positionChanged(oldPosition, newPosition);
    }

    public boolean checkIfLives(){
        return this.energy > 0;
    }



    public void gainEnergy(int energyBoost){
        this.energy+=energyBoost;
        if(this.energy>EvolvingAnimal.maxEnergyLimit)   this.energy=EvolvingAnimal.maxEnergyLimit;
    }

    @Override
    public boolean isPlant(){
        return false;
    }

    public void loseEnergy(int lostEnergy){
        this.energy-=lostEnergy;
    }

    public boolean canReproduce(){
        return (this.energy >=EvolvingAnimal.minEnergyToReproduce);
    }

    public EvolvingAnimal reproduceWith(EvolvingAnimal other){
        Genotype childGenes = this.genotype.mixGenotype(other.genotype);
        Vector2d childPosition = this.map.findChildPosition(other);
        int myEnergyLoss=this.energy/4;
        int othersEnergyLoss=other.energy/4;
        this.loseEnergy(myEnergyLoss);
        other.loseEnergy(othersEnergyLoss);
        return new EvolvingAnimal(this.map, childPosition, childGenes,myEnergyLoss + othersEnergyLoss);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver obs : this.observers)
            obs.positionChanged(oldPosition,newPosition, this);
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }


    public String genesToString(){
        return this.genotype.toString();
    }












}
