import agh.cs.project.lab8.Classes.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.jgroups.util.Util.*;

public class EvolvingAnimalTest {

    public EvolvingAnimalTest(){
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

    // Jako, że te obiekty tworzą się przed wywołaniem konstruktora tej klasy, w paru miejscach ponownie musimy inicjalizować te obiekty
    ModuloMap testMap = new ModuloMap(new Vector2d(4,4));
    Vector2d pos1 = new Vector2d(1,1);
    Vector2d pos2 = new Vector2d(2,2);
    EvolvingAnimal testAnimal1= new EvolvingAnimal(testMap, pos1);
    EvolvingAnimal testAnimal2= new EvolvingAnimal(testMap, pos2);

    @Test
    public void isPlantTest(){
        assertFalse(testAnimal1.isPlant());
        assertFalse(testAnimal2.isPlant());
    }

    @Test
    public void gainEnergyTest(){
        testAnimal1 = new EvolvingAnimal(testMap,pos1);
        testAnimal1.gainEnergy(100);
        assertEquals(testAnimal1.getEnergy(), EvolvingAnimal.maxEnergyLimit);
        testAnimal2 = new EvolvingAnimal(testMap,pos2);
        testAnimal2.gainEnergy(417);
        assertEquals(testAnimal2.getEnergy(), EvolvingAnimal.maxEnergyLimit);
        EvolvingAnimal testAnimal3 = new EvolvingAnimal(testMap, pos1, new Genotype(), 0);
        testAnimal3.gainEnergy(1);
        assertEquals(testAnimal3.getEnergy(), 1);
    }

    @Test
    public void loseEnergyTest(){
        testAnimal1 = new EvolvingAnimal(testMap,pos1);
        testAnimal1.loseEnergy(10);
        assertEquals(testAnimal1.getEnergy(),OptionParser.startEnergy-10);
        testAnimal2 = new EvolvingAnimal(testMap,pos2);
        testAnimal2.loseEnergy(24);
        assertEquals(testAnimal2.getEnergy(),OptionParser.startEnergy-24);
    }

    @Test
    public void checkIfLivesTest(){
        testAnimal1 = new EvolvingAnimal(testMap,pos1);
        assertTrue(testAnimal1.checkIfLives());
        testAnimal1.loseEnergy(OptionParser.startEnergy);
        assertFalse(testAnimal1.checkIfLives());
    }

    @Test public void canReproduceTest(){
        testAnimal1 = new EvolvingAnimal(testMap,pos1);
        assertTrue(testAnimal1.canReproduce());
        testAnimal1.loseEnergy(3*OptionParser.startEnergy/4);
        assertFalse(testAnimal1.canReproduce());
    }
}
