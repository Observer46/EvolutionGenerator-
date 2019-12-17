import agh.cs.lab3.*;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.RectangularMap;
import agh.cs.project.lab8.Classes.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AnimalIntegrationTest {


    @Test
    public void orientationIntegrationTest() {
        String[] args = {"f", "r"};

        OptionsParser opt = new OptionsParser();

        MoveDirection[] moves = opt.parse(args);
        IWorldMap map = new RectangularMap(10,10);
        map.place(new Animal(map));
        Animal zyzio = ((RectangularMap) map).getAnimals().get(0);
        assertEquals(MapDirection.NORTH, zyzio.getOrientation());
        MapDirection[] orients = {MapDirection.NORTH, MapDirection.EAST};
        zyzio.move(moves[0]);
        assertEquals(MapDirection.NORTH, zyzio.getOrientation());
        zyzio.move(moves[1]);
        assertEquals(MapDirection.EAST, zyzio.getOrientation());
    }
    @Test
    public void moveIntegrationTest(){
        String[] args = {"f", "b"};
        OptionsParser opt = new OptionsParser();
        MoveDirection[] moves = opt.parse(args);
        IWorldMap map = new RectangularMap(10,10);
        map.place(new Animal(map));
        Animal zyzio = ((RectangularMap) map).getAnimals().get(0);
        Vector2d start = new Vector2d(2,2);
        Vector2d end = new Vector2d(2,3);
        assertEquals(start, zyzio.getPosition());
        zyzio.move(moves[0]);
        assertEquals(end, zyzio.getPosition());
        System.out.println(zyzio.getPosition().toString());


    }
    @Test
    public void mapIntegrationTest(){
        String[] args = {"f", "f","b"};
        OptionsParser opt = new OptionsParser();
        MoveDirection[] moves = opt.parse(args);
        IWorldMap map = new RectangularMap(10,10);
        map.place(new Animal(map));
        Animal zyzio = ((RectangularMap) map).getAnimals().get(0);
        Vector2d pos1 = new Vector2d(2,3);
        Vector2d pos2 = new Vector2d(2,4);
        zyzio.move(moves[0]);
        assertEquals(pos1, zyzio.getPosition());
        zyzio.move(moves[1]);
        assertEquals(pos2, zyzio.getPosition());
        zyzio.move(moves[2]);
        assertEquals(pos1, zyzio.getPosition());

    }
    @Test
    public void parseIntegrationTest(){
        String[] args = {"f","b", "right"};
        OptionsParser opt = new OptionsParser();
        MoveDirection[] moves = opt.parse(args);
        assertEquals(MoveDirection.FORWARD, moves[0]);
        assertEquals(MoveDirection.BACKWARD, moves[1]);
        assertEquals(MoveDirection.RIGHT, moves[2]);


    }





}
