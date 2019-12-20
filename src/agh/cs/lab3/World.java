package agh.cs.lab3;
import agh.cs.lab4.*;
import agh.cs.lab5.*;
import agh.cs.project.lab8.Classes.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
//        Animal dyzio = new Animal();
////        System.out.println(dyzio.toString());
////        dyzio.move(MoveDirection.RIGHT);
////        System.out.println(dyzio.toString());
////        dyzio.move(MoveDirection.FORWARD);
////        System.out.println(dyzio.toString());
////        dyzio.move(MoveDirection.FORWARD);
////        System.out.println(dyzio.toString());
////        dyzio.move(MoveDirection.FORWARD);
////        System.out.println(dyzio.toString());
//        OptionsParser opt=new OptionsParser();
//        MoveDirection[] moves=opt.parse(args);
//        for(MoveDirection mo : moves){
//            dyzio.move(mo);
//            System.out.println(dyzio.toString());
//        }
        try {
            MoveDirection[] directions = new OptionsParser().parse(args);
            List<Rock> rocks = new ArrayList<>();
            rocks.add(new Rock(new Vector2d(-4, -4)));
            rocks.add(new Rock(new Vector2d(7, 7)));
            rocks.add(new Rock(new Vector2d(3, 6)));
            rocks.add(new Rock(new Vector2d(2, 0)));
            IWorldMap map = new UnboundedMap(rocks);
            map.place(new Animal(map));
            map.place(new Animal(map, new Vector2d(3,4)));
            System.out.println(map.toString());
            map.run(directions);
            System.out.println(map.toString());
        } catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }

    }
}
