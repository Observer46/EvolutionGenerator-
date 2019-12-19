package agh.cs.project.lab8.Classes;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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

    public void plantsGrowthPhase() { //TODO
        Vector2d plantInJunglePos = this.getUnoccupiedRandomPosInJungle();
        if (plantInJunglePos!=null){
        Plant plantInJungle = new Plant(plantInJunglePos);
        this.place(plantInJungle);
        }

        Vector2d plantOnSavannahPos = this.getUnoccupiedRandomPosOnSavannah();
        if(plantOnSavannahPos!=null) {
            Plant plantOnSavannah = new Plant(plantInJunglePos);
            this.place(plantOnSavannah);
        }

    }

    public boolean isInJungle(Vector2d position){
        return position.precedes(this.jungleUpperRight) && position.follows(this.jungleLowerLeft);
    }

    public Vector2d getRandomPositionInRange(Vector2d lowerLeft, Vector2d upperRight){
        int xRange=upperRight.x-lowerLeft.x;
        int yRange=upperRight.y-lowerLeft.y;
        int RandomPositionX=this.random.nextInt(xRange)+lowerLeft.x;
        int RandomPositionY=this.random.nextInt(yRange)+lowerLeft.y;
        return new Vector2d(RandomPositionX, RandomPositionY);
    }

    public Vector2d getRandomPositionInRangeNoCollision(Vector2d lowerLeft, Vector2d upperRight){
        int xRange=upperRight.x-lowerLeft.x;
        int yRange=upperRight.y-lowerLeft.y;
        int attemptCount = xRange*yRange;
        if(attemptCount > 100)  attemptCount=100;   // Ustalamy limit losowania aby przypadkiem nie zawiesił się program
        Vector2d randomPosition;
        do {
            randomPosition=this.getRandomPositionInRange(lowerLeft,upperRight);
            attemptCount--;
        }while (this.isOccupied(randomPosition) && attemptCount>0);
        return randomPosition;
    }

    public Vector2d getUnoccupiedRandomPosInJungle(){     // Zwraca null jeśli nie znalazło wolnej pozycji
        Vector2d randPosInJungle=this.getRandomPositionInRangeNoCollision(this.jungleLowerLeft,this.jungleUpperRight);
        return !this.isOccupied(randPosInJungle) ? randPosInJungle : null;
    }

    public Vector2d getUnoccupiedRandomPosOnSavannah(){   // Zwaraca null jeśli nie znalazło wolnej pozycji
        Vector2d randPosOnSavannah;
        int attemptCount=this.upperRight.x*this.upperRight.y;
        if(attemptCount>200)   attemptCount=200;  // Ustalamy górny limit losowań
        do{
            randPosOnSavannah=getRandomPositionInRangeNoCollision(this.lowerLeft,this.upperRight);
            attemptCount--;
        }while ((this.isOccupied(randPosOnSavannah) || this.isInJungle(randPosOnSavannah)) && attemptCount>0);  // Losujemy tak długo, jak pozycja może być zajęta (potencjalnie) lub jest w dżungli
        return !this.isOccupied(randPosOnSavannah) && !this.isInJungle(randPosOnSavannah) ? randPosOnSavannah : null;
    }


    public void placeFirstAnimals(){    // Jakie są wymagania co do początku???
        for(int i=0;i<OptionParser.startAnimalNumber;i++) {
            Vector2d animalPosition=this.getRandomPositionInRangeNoCollision(this.lowerLeft,this.upperRight);
            EvolvingAnimal animal = new EvolvingAnimal(this,animalPosition);
        }
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
