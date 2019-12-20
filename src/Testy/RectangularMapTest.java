import agh.cs.lab3.Animal;
import agh.cs.lab3.MoveDirection;
import agh.cs.lab3.OptionsParser;
import agh.cs.project.lab8.Classes.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.RectangularMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    @Test
    public void canMoveToTest(){

        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        assertTrue(map.canMoveTo(new Vector2d(3,2)));
        assertFalse(map.canMoveTo(new Vector2d(3,100)));
        assertFalse(map.canMoveTo(new Vector2d(3,4)));
    }

    @Test
    public void isOccupiedTest(){

        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertFalse(map.isOccupied(new Vector2d(3,3)));
        assertFalse(map.isOccupied(new Vector2d(3,2)));
        assertFalse(map.isOccupied(new Vector2d(3,100)));
        assertTrue(map.isOccupied(new Vector2d(3,4)));
    }

    @Test
    public void objectAtTest(){

        IWorldMap map = new RectangularMap(10, 5);
        Vector2d position1 = new Vector2d(2,2);
        Vector2d position2= new Vector2d(3,4);
        map.place(new Animal(map));
        map.place(new Animal(map,position2));

        Animal res1 = (Animal) map.objectAt(position1);
        assertTrue(res1.getPosition().equals(position1));
        assertTrue(null==map.objectAt(new Vector2d(3,3)));
        assertTrue(null==map.objectAt(new Vector2d(3,2)));
        assertTrue(null==map.objectAt(new Vector2d(3,100)));
        Animal res2 = (Animal) map.objectAt(position2);
        assertTrue(res2.getPosition().equals(position2));
    }

    @Test
    public void placeTest(){

        IWorldMap map = new RectangularMap(10, 5);
        Vector2d position1= new Vector2d(3,4);
        Vector2d position2 = new Vector2d(5,5);
        assertTrue(map.place(new Animal(map)));
        assertTrue(map.place(new Animal(map,position1)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map)));
        assertTrue(map.place(new Animal(map, position2)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map, position2)));
        assertThrows(IllegalArgumentException.class,()-> map.place(new Animal(map, new Vector2d(11,11))));

    }

    @Test
    public void runTest(){

        IWorldMap map = new RectangularMap(10, 5);
        String[] args = {"f","f","r","r","b","f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(2,3)));
        map.run(directions);
        Vector2d position1 = new Vector2d(1,2);
        Vector2d position2 = new Vector2d(3,4);
        assertEquals(position1, ((RectangularMap) map).getAnimals().get(0).getPosition());
        assertEquals(position2, ((RectangularMap) map).getAnimals().get(1).getPosition());
    }




}
