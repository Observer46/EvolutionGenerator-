package agh.cs.project.lab8.Classes;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModuloMap implements IPositionChangeObserver{
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight;
    private List<EvolvingAnimal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private ListMultimap<Vector2d,IMapElement> elements = ArrayListMultimap.create();
    private List<EvolvingAnimal> deadAnimals = new ArrayList<>();
    private Random random = new Random();
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private MapStatistics mapStats;

    public ModuloMap(Vector2d upperRight){
        this.upperRight=upperRight;
        this.jungleLowerLeft = this.findJungleLowerLeft();
        this.jungleUpperRight = this.findJungleUpperRight();
        this.mapStats = new MapStatistics(OptionParser.startAnimalNumber);
    }

    public Vector2d verifyMove(Vector2d position) { // Nie testowane
        Vector2d verifiedPosition=position;
        if(!position.precedes(upperRight)){
            verifiedPosition=new Vector2d(position.x%upperRight.x,position.y%upperRight.y);
        }
        if(!position.follows(lowerLeft)){
            verifiedPosition=new Vector2d(position.x%lowerLeft.x,position.y%lowerLeft.y);
        }
        return verifiedPosition;
    }

    private Vector2d getCenter(){ // Raczej ok
        return new Vector2d(this.upperRight.x/2,this.upperRight.y/2);
    }

    private Vector2d findJungleLowerLeft(){ // Raczej ok
        Vector2d center = this.getCenter();
        int jungleLowerLeftX = center.x - this.upperRight.x *(OptionParser.jungleRatioWidth/100)/2;
        int jungleLowerLeftY = center.y - this.upperRight.y *(OptionParser.jungleRatioHeight/100)/2;
        return new Vector2d(jungleLowerLeftX,jungleLowerLeftY);
    }

    private Vector2d findJungleUpperRight(){ // Raczej ok
        Vector2d center = this.getCenter();
        int jungleUpperRightX = center.x + this.upperRight.x *(OptionParser.jungleRatioWidth/100)/2;
        int jungleUpperRightY = center.y + this.upperRight.y *(OptionParser.jungleRatioHeight/100)/2;
        return new Vector2d(jungleUpperRightX,jungleUpperRightY);
    }

    public Vector2d findChildPosition(EvolvingAnimal parent){ // Raczej ok
        int birthDirectionID = random.nextInt(8);
        MapDirection birthDirection = MapDirection.convertToMapDir(birthDirectionID);

        Vector2d parentPosition = parent.getPosition();
        Vector2d childPosition = parentPosition.add(birthDirection.dirToMove());
        Vector2d initialChildPosition = childPosition;
        while(this.isOccupied(childPosition)){
            birthDirection=birthDirection.next();
            childPosition = parentPosition.add(birthDirection.dirToMove());
            if(childPosition.equals(initialChildPosition))    break;  // Jeśli dookoła nie ma wolnego pola to przerywamy
        }
        return childPosition;
    }

    public void place(IMapElement element) { // Raczej ok
        this.elements.put(element.getPosition(),element);
        if (element instanceof EvolvingAnimal) {
            EvolvingAnimal animal = (EvolvingAnimal) element;
            this.animals.add(animal);
            this.addAsObserver(animal);
        }
        else if(element instanceof Plant){
            Plant plant = (Plant) element;
            this.plants.add(plant);
        }
        else throw new IllegalArgumentException("Unknown type of map element");
    }

    public void remove(IMapElement element) { // Raczej ok
        this.elements.remove(element.getPosition(),element);
        if (element instanceof EvolvingAnimal){
            EvolvingAnimal animal = (EvolvingAnimal) element;
            this.deadAnimals.add(animal);
            this.animals.remove(animal);
            this.stopObserving(animal);
        }
        else if(element instanceof Plant){
            Plant plant = (Plant) element;
            this.plants.remove(plant);
        }
        else throw new IllegalArgumentException("Unknown type of map element");
    }

    public List<IMapElement> objectsAt(Vector2d position) { // Raczej ok
        List <IMapElement> elementsOnPosition = this.elements.get(position);
        return elementsOnPosition;
    }

    public boolean isOccupied(Vector2d position){ // Raczej ok
        return objectsAt(position).size()==0;
    }

    // IT'S JUNGLE OUT THERE!

    public void animalMovePhase() {
        for(EvolvingAnimal animal : animals) {
            animal.move();
            animal.loseEnergy(OptionParser.moveEnergy);
        }
    }

    public void deleteDeadAnimalsPhase(){
        for(EvolvingAnimal animal : animals)
            if(!animal.checkIfLives())  this.remove(animal);
    }

    public void animalsEatPhase(){
        for(Plant plant : plants){
            List<IMapElement> elementsOnPlantPosition = objectsAt(plant.getPosition());
            if(elementsOnPlantPosition.size()>1){ // Jeśli jest więcej niż jeden element na pozycji rośliny to jest tam zwierzę
                List<EvolvingAnimal> maxEnergyAnimals = getMaxEnergyAnimalsAtPositiong(plant.getPosition());
                int splitEnergyBoost = Plant.energyBoost/maxEnergyAnimals.size();
                feedMaxEnergyAnimals(maxEnergyAnimals,splitEnergyBoost);
                plant.gotEaten();
            }
        }
    }

    public List<EvolvingAnimal> getMaxEnergyAnimalsAtPositiong(Vector2d position){
        List<EvolvingAnimal> maxEnergyAnimals = new ArrayList<>();
        List<IMapElement> objectsAtPosition = this.objectsAt(position);
        int maxEnergyFound = 0;
        for(IMapElement elem : objectsAtPosition)
            if(!elem.isPlant()){
                EvolvingAnimal animal = (EvolvingAnimal) elem;
                if(animal.getEnergy() > maxEnergyFound)
                    maxEnergyAnimals.clear();
                if(animal.getEnergy() >= maxEnergyFound)    maxEnergyAnimals.add(animal);
            }
        return maxEnergyAnimals;
    }

    public void feedMaxEnergyAnimals(List<EvolvingAnimal> maxEnergyAnimals, int splitEnergyBoost){
        for(EvolvingAnimal animal : maxEnergyAnimals)
            animal.gainEnergy(splitEnergyBoost);
    }

    public void animalReporductionPhase(){// TODO

    }

    public void plantsGrowthPhase(){ //TODO

    }


    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, EvolvingAnimal animal) {
        this.elements.remove(oldPosition,animal);
        this.elements.put(newPosition,animal);
    }

    private void addAsObserver(EvolvingAnimal animal){
        animal.addObserver(this);
    }

    private void stopObserving(EvolvingAnimal animal){
        animal.removeObserver(this);
    }


    public String toString(){
        return "";
    }
}
