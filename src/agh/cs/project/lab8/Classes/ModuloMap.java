package agh.cs.project.lab8.Classes;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;


import java.util.*;

public class ModuloMap implements IPositionChangeObserver{
    private final Vector2d lowerLeft = new Vector2d(0,0);
    private final Vector2d upperRight;
    private List<EvolvingAnimal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();
    private ListMultimap<Vector2d,IMapElement> elements = ArrayListMultimap.create();
    private List<EvolvingAnimal> deadAnimals = new ArrayList<>();
    private Random random = new Random();
    private static int jungleRatioWidth;
    private static int jungleRatioHeight;
    private static Vector2d jungleLowerLeft;
    private static Vector2d jungleUpperRight;
    private MapStatistics mapStats;
    private EvolvingAnimal observedAnimal=null;


    public ModuloMap(Vector2d upperRight){
        this.upperRight=upperRight;
        ModuloMap.jungleRatioHeight = OptionParser.jungleRatioHeight;
        ModuloMap.jungleRatioWidth = OptionParser.jungleRatioWidth;
        this.jungleLowerLeft = this.findJungleLowerLeft();
        this.jungleUpperRight = this.findJungleUpperRight();
        this.mapStats = new MapStatistics(this);
    }

    public ListMultimap<Vector2d,IMapElement> getElements(){
        return this.elements;
    }

    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    public Vector2d getUpperRight(){
        return this.upperRight;
    }

    public List<EvolvingAnimal> getDeadAnimals(){
        return this.deadAnimals;
    }
    public List<EvolvingAnimal> getAnimals(){
        return this.animals;
    }
    public List<Plant> getPlants(){
        return this.plants;
    }

    public MapStatistics getMapStats(){
        return this.mapStats;
    }

    public void setObservedAnimal(Vector2d animalPosition){
        List <EvolvingAnimal> maxEnergyAnimalsOnPos = this.getMaxEnergyAnimalsAtPosition(animalPosition);
        this.observedAnimal = maxEnergyAnimalsOnPos.get(0);
        this.observedAnimal.startObserving();
    }

    public Vector2d verifyMove(Vector2d position) {
        Vector2d verifiedPosition=position;
        if(!position.precedes(upperRight)){
            verifiedPosition=new Vector2d(verifiedPosition.x%upperRight.x,verifiedPosition.y%upperRight.y);
        }
        if(!position.follows(lowerLeft)){
            if(verifiedPosition.x<lowerLeft.x)
                verifiedPosition=new Vector2d(verifiedPosition.x + upperRight.x,verifiedPosition.y);
            if (verifiedPosition.y<lowerLeft.y)
                verifiedPosition=new Vector2d(verifiedPosition.x,verifiedPosition.y + upperRight.y);
        }
        return verifiedPosition;
    }

    private Vector2d getCenter(){ // Raczej ok
        return new Vector2d(this.upperRight.x/2,this.upperRight.y/2);
    }

    private Vector2d findJungleLowerLeft(){
        Vector2d center = this.getCenter();
        int jungleLowerLeftX = center.x - this.upperRight.x *ModuloMap.jungleRatioWidth/100/2;
        int jungleLowerLeftY = center.y - this.upperRight.y *ModuloMap.jungleRatioHeight/100/2;
        return new Vector2d(jungleLowerLeftX,jungleLowerLeftY);
    }

    private Vector2d findJungleUpperRight(){
        Vector2d center = this.getCenter();
        int jungleUpperRightX = center.x + this.upperRight.x *ModuloMap.jungleRatioWidth/100/2;
        int jungleUpperRightY = center.y + this.upperRight.y *ModuloMap.jungleRatioHeight/100/2;
        return new Vector2d(jungleUpperRightX,jungleUpperRightY);
    }

    public Vector2d findChildPosition(EvolvingAnimal parent){
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

    public void place(IMapElement element) {
        this.elements.put(element.getPosition(),element);
        if (element instanceof EvolvingAnimal) {
            EvolvingAnimal animal = (EvolvingAnimal) element;
            this.animals.add(animal);
            this.addAsObserver(animal);
            this.mapStats.addAnimalToStats(animal);
        }
        else if(element instanceof Plant){
            Plant plant = (Plant) element;
            this.plants.add(plant);
        }
        else throw new IllegalArgumentException("Unknown type of map element");
    }

    public void remove(IMapElement element) {
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

    public List<IMapElement> objectsAt(Vector2d position) {
        List <IMapElement> elementsOnPosition = this.elements.get(position);
        return elementsOnPosition;
    }

    public boolean isOccupied(Vector2d position){ // Działa
        return !(objectsAt(position).size()==0);
    }


    public void animalMovePhase() {
        for(EvolvingAnimal animal : animals) {
            animal.move();
            animal.loseEnergy(OptionParser.moveEnergy);
        }
    }

    public void deleteDeadAnimalsPhase(){
        List<EvolvingAnimal> animalsToDelete = new ArrayList<>();
        for(EvolvingAnimal animal : animals)
            if(!animal.checkIfLives())
                animalsToDelete.add(animal);

        for (EvolvingAnimal animal : animalsToDelete) {
            int currentEra = this.mapStats.getEra();
            animal.getAnimalStats().setDeathDate(currentEra);
            remove(animal);
        }
    }

    public void animalsEatPhase(){
        List <Plant> eatenPlants = new ArrayList<>();
        for(Plant plant : plants){
            List<IMapElement> elementsOnPlantPosition = objectsAt(plant.getPosition());
            if(elementsOnPlantPosition.size()>1){ // Jeśli jest więcej niż jeden element na pozycji rośliny to jest tam zwierzę
                List<EvolvingAnimal> maxEnergyAnimals = getMaxEnergyAnimalsAtPosition(plant.getPosition());
                int splitEnergyBoost = Plant.energyBoost/maxEnergyAnimals.size();
                feedMaxEnergyAnimals(maxEnergyAnimals,splitEnergyBoost);
                eatenPlants.add(plant);
            }
        }
        for(Plant plant : eatenPlants)  this.remove(plant);
    }

    public List<EvolvingAnimal> getMaxEnergyAnimalsAtPosition(Vector2d position){
        List<EvolvingAnimal> maxEnergyAnimals = new ArrayList<>();
        List<IMapElement> objectsAtPosition = this.objectsAt(position);
        int maxEnergyFound = 0;
        for(IMapElement elem : objectsAtPosition)
            if(!elem.isPlant()){
                EvolvingAnimal animal = (EvolvingAnimal) elem;
                if(animal.getEnergy() > maxEnergyFound)
                    maxEnergyAnimals.clear();
                if(animal.getEnergy() >= maxEnergyFound){
                    maxEnergyAnimals.add(animal);
                    maxEnergyFound=animal.getEnergy();
                }
            }
        return maxEnergyAnimals;
    }

    public void feedMaxEnergyAnimals(List<EvolvingAnimal> maxEnergyAnimals, int splitEnergyBoost){
        for(EvolvingAnimal animal : maxEnergyAnimals)
            animal.gainEnergy(splitEnergyBoost);
    }

    public List<EvolvingAnimal> findSecondMaxEnergyAnimals(List<IMapElement> elementsOnPosition, int maxEnergy){
        List<EvolvingAnimal> secondMaxEnergyAnimals = new ArrayList<>();
        int secondMaxEnergy=0;
        for(IMapElement elem : elementsOnPosition)
            if(!elem.isPlant()){
                EvolvingAnimal animal = (EvolvingAnimal) elem;
                if(animal.getEnergy() > secondMaxEnergy && animal.getEnergy() < maxEnergy)
                    secondMaxEnergyAnimals.clear();
                if(animal.getEnergy() >= secondMaxEnergy && animal.getEnergy() < maxEnergy){
                    secondMaxEnergyAnimals.add(animal);
                    secondMaxEnergy=animal.getEnergy();
                }
            }
        return secondMaxEnergyAnimals;
    }

    public void eraPasses(){
        this.deleteDeadAnimalsPhase();
        this.animalMovePhase();
        this.animalsEatPhase();
        this.animalReporductionPhase();
        this.plantsGrowthPhase();
        this.mapStats.eraPassed();
    }

    public List<EvolvingAnimal> findAnimalsToReproduce(List<IMapElement> elementsOnPosition){
        Vector2d thisPosition = elementsOnPosition.get(0).getPosition();       // Aktualna pozycja
        List<EvolvingAnimal> animalsWithMaxEnergy = this.getMaxEnergyAnimalsAtPosition(thisPosition);
        if (animalsWithMaxEnergy.size() >=2){
            if (animalsWithMaxEnergy.get(0).canReproduce()) return animalsWithMaxEnergy;       // Jeśli zwierząta z maksymalną energią (co najmniej dwa) mogą się rozmnażać to z nich będziemy losować parę do rozmnażania
            else    return null;    // Jeśli maksymalne mają za małą energię to nie znajdziemy pary do rozmnażania
        }
        else if (animalsWithMaxEnergy.size()==0)    return null;    // Brak zwierząt na pozycji
        //Przypadek dla jednego z najwyższą enegrią na polu
        if(!animalsWithMaxEnergy.get(0).canReproduce()) return null;
        EvolvingAnimal maxEnergyParent = animalsWithMaxEnergy.get(0);
        int maxEnergy = maxEnergyParent.getEnergy();
        List<EvolvingAnimal> animalsToReproduction = findSecondMaxEnergyAnimals(elementsOnPosition,maxEnergy);
        if (animalsToReproduction.size()>=1)
            if(!animalsToReproduction.get(0).canReproduce())        // Sprawdzamy, czy drugi rodzic(drudzy rodzice) mogą też się rozmnażać
                return null;

        animalsWithMaxEnergy.addAll(animalsToReproduction);         // W ten sposób zwierzę o najwyższej energii zawsze jest na początku
        return animalsWithMaxEnergy.size()==1 ? null : animalsWithMaxEnergy;
    }

    public void tryToReproduce (List<EvolvingAnimal> animalsToReproduce){
        if(animalsToReproduce==null)   return;
        EvolvingAnimal parent1=animalsToReproduce.get(0);
        EvolvingAnimal parent2=animalsToReproduce.get(1);
        EvolvingAnimal child;
        int firstParentIndex=0;
        int secondParentIndex=1;
        if(parent1.getEnergy()!=parent2.getEnergy()){       // Dwójka najmocniejszych się rozmnaża, jest kilka "drugich najmocniejszych", więc losoujemy go
            if (animalsToReproduce.size()>=2)
                secondParentIndex = random.nextInt(animalsToReproduce.size() -1) + 1;   // Nie wylosujemy nigdy parent1 dzięki przesunięciu zakresu
        }
        else{       // Losujemy dwóch najmocniejszych
            if (animalsToReproduce.size()>2) {
                firstParentIndex = random.nextInt(animalsToReproduce.size());
                secondParentIndex = random.nextInt(animalsToReproduce.size() - firstParentIndex) + firstParentIndex;   // Nie wylosujemy nigdy parent1 dzięki przesunięciu zakresu
            }
        }
        parent1 = animalsToReproduce.get(firstParentIndex);
        parent2 = animalsToReproduce.get(secondParentIndex);
        child = parent1.reproduceWith(parent2);
        this.place(child);
    }



    public void animalReporductionPhase(){
        Set<Vector2d> nonEmptyPositions = this.elements.keySet();
        List <Vector2d> listOfNonEmptyPositions = new ArrayList<>();
        for(Vector2d position : nonEmptyPositions)
            listOfNonEmptyPositions.add(position);

        for(Vector2d position : listOfNonEmptyPositions){
            List <IMapElement>  elementsOnPositions = this.elements.get(position);      // Lista elementów na pozycji gdzie cokolwiek aktualnie się znaduje
            List <EvolvingAnimal> animalsToReproduction = findAnimalsToReproduce(elementsOnPositions);
            this.tryToReproduce(animalsToReproduction);
        }
    }

    public void plantsGrowthPhase() {
        Vector2d plantInJunglePos = this.getUnoccupiedRandomPosInJungle();
        if (plantInJunglePos!=null){
            Plant plantInJungle = new Plant(plantInJunglePos, this);
            this.place(plantInJungle);
        }

        Vector2d plantOnSavannahPos = this.getUnoccupiedRandomPosOnSavannah();
        if(plantOnSavannahPos!=null) {
            Plant plantOnSavannah = new Plant(plantOnSavannahPos, this);
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


    public void placeFirstAnimals(){
        for(int i=0;i<OptionParser.startAnimalNumber;i++) {
            Vector2d animalPosition=this.getRandomPositionInRangeNoCollision(this.lowerLeft,this.upperRight);
            EvolvingAnimal animal = new EvolvingAnimal(this,animalPosition);
            this.place(animal);
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

    public boolean areAnyAliveAnimals(){
        return this.mapStats.getAnimalCount()>0;
    }

    public String writeMapStats(){
        return this.mapStats.toString();
    }


    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        String s="";
        s+= this.mapStats.toString() + "\n"+visualizer.draw(lowerLeft,upperRight);
        return s;
    }

    public String getObservedAnimalStats(){
        if(this.observedAnimal!=null)   return this.observedAnimal.getAnimalStats().printObservedStats();
        return "";
    }
}
