import agh.cs.project.lab8.Classes.*;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.jgroups.util.Util.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalAndMapIntegrationTest {
    public AnimalAndMapIntegrationTest()  {
        try {
            OptionParser options = new OptionParser();
        }catch (FileNotFoundException ex1){
            System.out.println(ex1.getMessage());
            ex1.printStackTrace();
        }catch (IOException ex2){
            System.out.println(ex2.getMessage());
            ex2.printStackTrace();
        }catch (ParseException ex3){
            System.out.println(ex3.getMessage());
            ex3.printStackTrace();
        }
    }

    ModuloMap smallTestMap = new ModuloMap(new Vector2d(4,4));
    Vector2d pos1 = new Vector2d(1,1);
    Vector2d pos2 = new Vector2d(2,3);
    Vector2d pos3 = new Vector2d(3,3);
    EvolvingAnimal animal1 = new EvolvingAnimal(smallTestMap,pos1);
    EvolvingAnimal animal2 = new EvolvingAnimal(smallTestMap,pos2);
    EvolvingAnimal animal3 = new EvolvingAnimal(smallTestMap,pos3);
    Plant plant = new Plant(pos3, smallTestMap);

    @Test
    public void elementsInitTest(){
        //assertTrue(OptionParser.startEnergy==30);
        smallTestMap. place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(plant);
        List <IMapElement> animalsOnPos1 = smallTestMap.objectsAt(pos1);
        EvolvingAnimal checkAnimalOnPos1 = (EvolvingAnimal) animalsOnPos1.get(0);
        assertEquals(checkAnimalOnPos1,animal1);

        List <IMapElement> animalsOnPos2 = smallTestMap.objectsAt(pos2);
        EvolvingAnimal checkAnimalOnPos2 = (EvolvingAnimal) animalsOnPos2.get(0);
        assertEquals(checkAnimalOnPos2,animal2);

        List <IMapElement> plantsOnPos3 = smallTestMap.objectsAt(pos3);
        Plant plantOnPos3 = (Plant) plantsOnPos3.get(0);
        assertEquals(plantOnPos3,plant);

        EvolvingAnimal animal3 = new EvolvingAnimal(smallTestMap, pos1);
        smallTestMap.place(animal3);
        animalsOnPos1 = smallTestMap.objectsAt(pos1);
        checkAnimalOnPos1 = (EvolvingAnimal) animalsOnPos1.get(1);
        assertEquals(checkAnimalOnPos1,animal3);
    }

    @Test
    public void animalMoveTest(){
        smallTestMap.place(animal3);
        Vector2d outOfBoundPosition = new Vector2d(4,4);
        Vector2d expectedPosition = new Vector2d(0,0);
        animal3.moveToVerifiedPosition(animal3.getPosition(),outOfBoundPosition);
        assertEquals(animal3.getPosition(),expectedPosition);

        outOfBoundPosition = new Vector2d(3,4);
        expectedPosition = new Vector2d(3,0);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallTestMap,new Vector2d(3,3));
        smallTestMap.place(animal4);
        animal4.moveToVerifiedPosition(animal4.getPosition(),outOfBoundPosition);
        assertEquals(animal4.getPosition(),expectedPosition);

        outOfBoundPosition = new Vector2d(-1,-1);
        expectedPosition = new Vector2d(3,3);
        EvolvingAnimal animal5 = new EvolvingAnimal(smallTestMap,new Vector2d(0,0));
        smallTestMap.place(animal5);
        animal5.moveToVerifiedPosition(animal5.getPosition(),outOfBoundPosition);
        assertEquals(animal5.getPosition(),expectedPosition);
    }

    @Test
    public void plantGrowthPhaseTest(){
        ModuloMap smallTestMap = new ModuloMap(new Vector2d(4,4));
        smallTestMap.plantsGrowthPhase();
        ListMultimap<Vector2d,IMapElement> elementsOnMap = smallTestMap.getElements();
        assertTrue(elementsOnMap.values().size()==2);
        boolean oneInJungle=false;
        boolean oneOnSavannah=false;
        for (IMapElement element : elementsOnMap.values()) {
            //System.out.println(element.getPosition());
            Plant plant = (Plant) element;
            if (smallTestMap.isInJungle(plant.getPosition())) {
                assertTrue(smallTestMap.isInJungle(plant.getPosition()));
                oneInJungle = true;
            }
            else {
                assertFalse(smallTestMap.isInJungle(plant.getPosition()));
                oneOnSavannah=true;
            }
        }
        assertTrue(oneInJungle);
        assertTrue(oneOnSavannah);
    }

    @Test
    public void deletedDeadAnimalsPhase(){
        smallTestMap = new ModuloMap(new Vector2d(4,4));
        animal1 = new EvolvingAnimal(smallTestMap,pos1);
        animal2 = new EvolvingAnimal(smallTestMap,pos2);
        animal3 = new EvolvingAnimal(smallTestMap,pos3);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(animal3);
        animal1.loseEnergy(EvolvingAnimal.maxEnergyLimit);
        animal3.loseEnergy(EvolvingAnimal.maxEnergyLimit);
        smallTestMap.deleteDeadAnimalsPhase();
        ListMultimap<Vector2d,IMapElement> elementsOnMap = smallTestMap.getElements();
        for(IMapElement elem : elementsOnMap.values()) System.out.println(elem);
        assertTrue(elementsOnMap.values().size()==1);
        List <EvolvingAnimal> deadAnimals = smallTestMap.getDeadAnimals();
        assertEquals(deadAnimals.get(0), animal1);
        assertEquals(deadAnimals.get(1),animal3);
    }

    @Test
    public void animalsEatPhaseTest(){
        smallTestMap = new ModuloMap(new Vector2d(4,4));
        animal1 = new EvolvingAnimal(smallTestMap,pos1);
        animal2 = new EvolvingAnimal(smallTestMap,pos1);
        animal3 = new EvolvingAnimal(smallTestMap,pos2);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallTestMap,pos2);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(animal3);
        smallTestMap.place(animal4);
        animal1.loseEnergy(15);
        animal2.loseEnergy(10);
        animal3.loseEnergy(15);
        animal4.loseEnergy(15);
        Plant plant1 = new Plant(pos1,smallTestMap );
        Plant plant2 = new Plant(pos2, smallTestMap);
        smallTestMap.place(plant1);
        smallTestMap.place(plant2);
//        ListMultimap<Vector2d,IMapElement> elementsOnMap = smallTestMap.getElements();
//        for(IMapElement elem : elementsOnMap.values()) System.out.println(elem);
        smallTestMap.animalsEatPhase();
        assertEquals(animal3.getEnergy(),animal4.getEnergy());
        assertEquals(animal3.getEnergy(),OptionParser.startEnergy-15+OptionParser.plantEnergy/2);
        assertEquals(animal1.getEnergy(),OptionParser.startEnergy-15);
        assertEquals(animal2.getEnergy(),OptionParser.startEnergy-10+OptionParser.plantEnergy);
    }

    @Test
    public void getMaxEnergyAnimalAtPositionTest(){
        smallTestMap = new ModuloMap(new Vector2d(4,4));
        animal1 = new EvolvingAnimal(smallTestMap,pos1);
        animal2 = new EvolvingAnimal(smallTestMap,pos1);
        animal3 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallTestMap,pos1);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(animal3);
        smallTestMap.place(animal4);
        animal1.loseEnergy(15);
        animal2.loseEnergy(10);
        animal3.loseEnergy(10);
        animal4.loseEnergy(10);
        Plant plant1 = new Plant(pos1, smallTestMap);
        smallTestMap.place(plant1);
        List <EvolvingAnimal> animalsWithMaxEnergy = smallTestMap.getMaxEnergyAnimalsAtPosition(pos1);
        assertTrue(animalsWithMaxEnergy.size()==3);
        assertEquals(animal2,animalsWithMaxEnergy.get(0));
        assertEquals(animal3,animalsWithMaxEnergy.get(1));
        assertEquals(animal4,animalsWithMaxEnergy.get(2));
    }

    @Test
    public void animalMovePhaseTest(){
        smallTestMap = new ModuloMap(new Vector2d(10,10));
        pos1 = new Vector2d(2,2);
        pos2 = new Vector2d(8,8);
        animal1 = new EvolvingAnimal(smallTestMap,pos1);
        animal2 = new EvolvingAnimal(smallTestMap,pos2);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.animalMovePhase();
        assertFalse(smallTestMap.isOccupied(pos1));
        assertFalse(smallTestMap.isOccupied(pos2));
        assertTrue(animal1.getEnergy()==OptionParser.startEnergy-OptionParser.moveEnergy);
        assertTrue(animal2.getEnergy()==OptionParser.startEnergy-OptionParser.moveEnergy);

        Vector2d animal1Position = animal1.getPosition();
        Vector2d animal2Position = animal2.getPosition();
        Vector2d lowerLeft = smallTestMap.getLowerLeft();
        Vector2d upperRight = smallTestMap.getUpperRight();
        assertTrue(animal1Position.follows(lowerLeft) && animal1Position.precedes(upperRight)); // Jest na mapie
        assertTrue(animal2Position.follows(lowerLeft) && animal2Position.precedes(upperRight));

        Vector2d pos1MoveBoundLowerLeft = new Vector2d(1,1);
        Vector2d pos1MoveBoundUpperRight = new Vector2d(4,4);
        assertTrue(animal1Position.follows(pos1MoveBoundLowerLeft) && animal1Position.precedes(pos1MoveBoundUpperRight));

        Vector2d pos2MoveBoundLowerLeft = new Vector2d(7,7);
        Vector2d pos2MoveBoundUpperRight = new Vector2d(10,10);
        assertTrue(animal2Position.follows(pos2MoveBoundLowerLeft) && animal2Position.precedes(pos2MoveBoundUpperRight));
    }

    @Test
    public void findSecondMaxEnergyAnimals(){
        smallTestMap = new ModuloMap(new Vector2d(10,10));
        EvolvingAnimal animal1 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal2 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal3 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal5 = new EvolvingAnimal(smallTestMap,pos1);
        Plant plant = new Plant(pos1, smallTestMap);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(animal3);
        smallTestMap.place(animal4);
        smallTestMap.place(animal5);
        smallTestMap.place(plant);

        animal1.loseEnergy(10);
        animal3.loseEnergy(10);
        animal4.loseEnergy(10);
        animal5.loseEnergy(20);
        List <IMapElement> elemnetsOnPos1 = smallTestMap.objectsAt(pos1);
        List <EvolvingAnimal> secondMaxEnergyAnimals = smallTestMap.findSecondMaxEnergyAnimals(elemnetsOnPos1, OptionParser.startEnergy);
        assertTrue(secondMaxEnergyAnimals.size()==3);

        assertTrue(secondMaxEnergyAnimals.contains(animal1));
        assertTrue(secondMaxEnergyAnimals.contains(animal3));
        assertTrue(secondMaxEnergyAnimals.contains(animal4));
        assertFalse(secondMaxEnergyAnimals.contains(animal2));
        assertFalse(secondMaxEnergyAnimals.contains(animal5));
        assertFalse(secondMaxEnergyAnimals.contains(plant));
    }

    @Test
    public void animalReproductionPhaseTest(){
        smallTestMap = new ModuloMap(new Vector2d(6,6));
        pos1 = new Vector2d(2,2);
        pos2 = new Vector2d(8,8);
        EvolvingAnimal animal1 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal2 = new EvolvingAnimal(smallTestMap,pos1);
        EvolvingAnimal animal3 = new EvolvingAnimal(smallTestMap,pos2);
        EvolvingAnimal animal4 = new EvolvingAnimal(smallTestMap,pos2);
        EvolvingAnimal animal5 = new EvolvingAnimal(smallTestMap,pos2);
        smallTestMap.place(animal1);
        smallTestMap.place(animal2);
        smallTestMap.place(animal3);
        smallTestMap.place(animal4);
        smallTestMap.place(animal5);
        animal5.loseEnergy(10); // Tylko animal1 i animal2 się rozmnażają
        animal3.loseEnergy(OptionParser.startEnergy);
        animal4.loseEnergy(OptionParser.startEnergy);
        smallTestMap.animalReporductionPhase();

        Multimap <Vector2d,IMapElement> elemnentsOnMap = smallTestMap.getElements();
        assertTrue(elemnentsOnMap.values().size()==6);
        assertTrue(animal1.getEnergy()==OptionParser.startEnergy - OptionParser.startEnergy/4);
        assertTrue(animal2.getEnergy()==OptionParser.startEnergy - OptionParser.startEnergy/4);
        assertTrue(elemnentsOnMap.keySet().size()==3);  // Dziecko wylądowało na nowej pozycji, która nie jest zajmowana
        EvolvingAnimal child = smallTestMap.getAnimals().get(5);

        assertTrue(child.getEnergy()==OptionParser.startEnergy/4 + OptionParser.startEnergy/4);
        Vector2d lowerLeftBirthBound = new Vector2d(1,1);
        Vector2d upperRightBirthBound = new Vector2d(4,4);
        assertTrue(child.getPosition().precedes(upperRightBirthBound));
        assertTrue(child.getPosition().follows(lowerLeftBirthBound));
    }

    @Test
    public void placeFirstAnimalsTest(){
        smallTestMap = new ModuloMap(new Vector2d(2,2));
        smallTestMap.placeFirstAnimals();
        assertTrue(smallTestMap.getAnimals().size()==4); // Ilość zadana w properties.json
        assertTrue(smallTestMap.getElements().keySet().size() == 4); // Czy każde zwierzę jest na innej pozycji
        assertTrue(smallTestMap.getElements().values().size()==4);

    }
}
