package agh.cs.project.lab8.Classes;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvolvingAnimal extends AbstractWorldMapElement {
    private Genotype genotype;
    private int energy;
    private MapDirection orientation;
    public static int minEnergyToReproduce;
    public static int maxEnergyLimit;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private AnimalStatistics animalStats;
    private boolean hasDominatingGenotype=false;
    private boolean isSelectedForObservation=false;



    public EvolvingAnimal (ModuloMap map, Vector2d position){
        super.map=map;
        this.genotype= new Genotype();
        super.position=position;
        int direction=new Random().nextInt(8);
        EvolvingAnimal.minEnergyToReproduce = OptionParser.startEnergy/2;
        EvolvingAnimal.maxEnergyLimit=OptionParser.startEnergy;
        this.energy=OptionParser.startEnergy;
        this.orientation=MapDirection.convertToMapDir(direction);
        this.animalStats=new AnimalStatistics(this);
    }

    public EvolvingAnimal(ModuloMap map, Vector2d position, Genotype genotype, int energy){
        this(map,position);
        this.energy=energy;
        this.genotype=genotype;
    }

    public AnimalStatistics getAnimalStats(){
        return this.animalStats;
    }

    public void startObserving(){
        this.isSelectedForObservation=true;
    }

    public void stopObserving(){
        this.isSelectedForObservation=false;
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
        this.moveToVerifiedPosition(oldPosition,newPosition);
    }

    public void moveToVerifiedPosition(Vector2d oldPosition,Vector2d movePosition){
        Vector2d verifiedPosition = super.map.verifyMove(movePosition);       // Weryfikujemy pozycję;
        super.position=verifiedPosition;
        this.positionChanged(oldPosition, verifiedPosition);
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
        childPosition=super.map.verifyMove(childPosition);
        int myEnergyLoss=this.energy/4;
        int othersEnergyLoss=other.energy/4;
        this.loseEnergy(myEnergyLoss);
        other.loseEnergy(othersEnergyLoss);
        EvolvingAnimal child = new EvolvingAnimal(this.map, childPosition, childGenes,myEnergyLoss + othersEnergyLoss);
        this.animalStats.becameAParent(child);
        other.animalStats.becameAParent(child);
        return child;
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

    @Override
    public String toString() {
        String s;
        if(this.energy>=EvolvingAnimal.maxEnergyLimit*2/3)
            s=Character.toString((char) 9787)+ " ";
        else if(this.energy>=EvolvingAnimal.maxEnergyLimit/3)
            s=Character.toString((char) 9786)+ " ";
        else
            s=Character.toString((char) 9785)+ " ";
        if(this.hasDominatingGenotype)
            s=Character.toString((char) 9854)+ " ";
        if(this.isSelectedForObservation){
            s=Character.toString((char) 9733)+ " ";
            if(!this.checkIfLives())
                s=Character.toString((char) 9760)+ " ";
        }
        return s;
    }
}
