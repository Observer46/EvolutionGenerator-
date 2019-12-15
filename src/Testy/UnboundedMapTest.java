import agh.cs.lab3.Animal;
import agh.cs.lab3.MoveDirection;
import agh.cs.lab3.OptionsParser;
import agh.cs.lab3.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab5.Rock;
import agh.cs.lab5.UnboundedMap;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnboundedMapTest {

    @Test
    public void canMoveToTest(){

        List <Rock> rocks = new ArrayList<>();
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2 = new Vector2d(3,4);
        Vector2d position3 = new Vector2d(4,4);
        Vector2d position4 = new Vector2d(5,5);


        rocks.add(new Rock(position3));
        rocks.add(new Rock(position4));
        IWorldMap map = new UnboundedMap(rocks);
        map.place(new Animal(map));
        map.place(new Animal(map,position2));
        assertFalse(map.canMoveTo(position1));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        assertTrue(map.canMoveTo(new Vector2d(3,2)));
        assertTrue(map.canMoveTo(new Vector2d(3,100)));
        assertFalse(map.canMoveTo(position2));
        assertFalse(map.canMoveTo((position3)));
        assertFalse(map.canMoveTo((position4)));
    }

    @Test
    public void isOccupiedTest(){

        List <Rock> rocks = new ArrayList<>();
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2 = new Vector2d(3,4);
        Vector2d position3 = new Vector2d(4,4);
        Vector2d position4 = new Vector2d(5,5);


        rocks.add(new Rock(position3));
        rocks.add(new Rock(position4));
        IWorldMap map = new UnboundedMap(rocks);
        map.place(new Animal(map));
        map.place(new Animal(map,position2));
        assertTrue(map.isOccupied(position1));
        assertFalse(map.isOccupied(new Vector2d(3,3)));
        assertFalse(map.isOccupied(new Vector2d(3,2)));
        assertFalse(map.isOccupied(new Vector2d(3,100)));
        assertTrue(map.isOccupied(position2));
        assertTrue(map.isOccupied(position3));
        assertTrue(map.isOccupied(position4));
    }

    @Test
    public void objectAtTest(){

        List <Rock> rocks = new ArrayList<>();
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2 = new Vector2d(3,4);
        Vector2d position3 = new Vector2d(4,4);
        Vector2d position4 = new Vector2d(5,5);


        rocks.add(new Rock(position3));
        rocks.add(new Rock(position4));
        IWorldMap map = new UnboundedMap(rocks);
        map.place(new Animal(map));
        map.place(new Animal(map,position2));

        Animal res1 = (Animal) map.objectAt(position1);
        assertTrue(res1.getPosition().equals(position1));
        assertTrue(null==map.objectAt(new Vector2d(3,3)));
        assertTrue(null==map.objectAt(new Vector2d(3,2)));
        assertTrue(null==map.objectAt(new Vector2d(3,100)));
        Animal res2 = (Animal) map.objectAt(position2);
        assertTrue(res2.getPosition().equals(position2));
        Rock res3 = (Rock) map.objectAt(position3);
        assertTrue(res3.getPosition().equals(position3));
        Rock res4 = (Rock) map.objectAt(position4);
        assertTrue(res4.getPosition().equals(position4));

    }

    @Test
    public void placeTest(){

        List <Rock> rocks = new ArrayList<>();
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2 = new Vector2d(3,4);
        Vector2d position3 = new Vector2d(4,4);
        Vector2d position4 = new Vector2d(5,5);


        rocks.add(new Rock(position3));
        rocks.add(new Rock(position4));
        IWorldMap map = new UnboundedMap(rocks);
        assertTrue(map.place(new Animal(map)));
        assertTrue(map.place(new Animal(map,position2)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map, position2)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map, position3)));
        assertTrue(map.place(new Animal(map, new Vector2d(11,11))));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map, position4)));


    }

    @Test
    public void runTest(){

        List <Rock> rocks = new ArrayList<>();
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2 = new Vector2d(2,3);
        Vector2d position3 = new Vector2d(2,4);
        Vector2d position4 = new Vector2d(1,2);


        rocks.add(new Rock(position3));
        rocks.add(new Rock(position4));
        IWorldMap map = new UnboundedMap(rocks);
        String[] args = {"f","f","r","r","b","f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        map.place(new Animal(map));
        map.place(new Animal(map, position2));
        map.run(directions);
        Vector2d position5 = new Vector2d(3,3);
        assertEquals(position1, ((UnboundedMap) map).getAnimals().get(0).getPosition());
        assertEquals(position5, ((UnboundedMap) map).getAnimals().get(1).getPosition());
    }




}

