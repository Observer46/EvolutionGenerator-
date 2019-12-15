package agh.cs.lab3;

import java.lang.reflect.Array;
import java.util.Arrays;

public class OptionsParser {

    public MoveDirection[] parse(String[] args){

        MoveDirection[] res = new MoveDirection[args.length];
        int i=0;
        for(String arg : args){
            switch (arg){
                case "forward":
                case "f":
                    res[i]=MoveDirection.FORWARD;
                    i++;
                    break;
                case "backward":
                case "b":
                    res[i]=MoveDirection.BACKWARD;
                    i++;
                    break;
                case "left":
                case "l":
                    res[i]=MoveDirection.LEFT;
                    i++;
                    break;
                case "right":
                case "r":
                    res[i]=MoveDirection.RIGHT;
                    i++;
                    break;
                default:
                    throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }

        return Arrays.copyOfRange(res, 0, i);
    }
}
