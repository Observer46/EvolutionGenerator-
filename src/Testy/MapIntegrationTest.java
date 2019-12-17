

import agh.cs.lab4.*;
import agh.cs.lab3.*;
import agh.cs.project.lab8.Classes.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapIntegrationTest {

    @Test
    public void MapTest(){

        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.run(directions);
        Vector2d pos1 = new Vector2d(2,0);
        Vector2d pos2 = new Vector2d(3,5);
        Animal zyzio = ((RectangularMap) map).getAnimals().get(0);
        Animal dyzio = ((RectangularMap) map).getAnimals().get(1);
        assertEquals(pos1,zyzio.getPosition());
        assertEquals(pos2,dyzio.getPosition());
        assertEquals(MapDirection.SOUTH, zyzio.getOrientation());
        assertEquals(MapDirection.NORTH, dyzio.getOrientation());


        String[] args2 = {"f","f","l","l","b"};
        MoveDirection[] directions2 = new OptionsParser().parse(args2);
        IWorldMap map2 = new RectangularMap(10, 10);
        map2.place(new Animal(map2));
        map2.place(new Animal(map2,new Vector2d(5,5)));
        map2.place(new Animal(map2, new Vector2d(2,10)));
        map2.run(directions2);
        pos1 = new Vector2d(2,3);
        pos2 = new Vector2d(5,5);
        Vector2d pos3 = new Vector2d(2,10);

        zyzio = ((RectangularMap) map2).getAnimals().get(0);
        dyzio = ((RectangularMap) map2).getAnimals().get(1);
        Animal hyzio = ((RectangularMap) map2).getAnimals().get(2);
        assertEquals(pos1,zyzio.getPosition());
        assertEquals(pos2,dyzio.getPosition());
        assertEquals(pos3,hyzio.getPosition());
        assertEquals(MapDirection.WEST, zyzio.getOrientation());
        assertEquals(MapDirection.NORTH, dyzio.getOrientation());
        assertEquals(MapDirection.WEST, hyzio.getOrientation());
    }
}
