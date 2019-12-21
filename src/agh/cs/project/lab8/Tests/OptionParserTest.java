import agh.cs.project.lab8.Classes.OptionParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.io.IOException;

import static agh.cs.project.lab8.Classes.OptionParser.*;
import static org.jgroups.util.Util.assertEquals;

public class OptionParserTest {
    @Test
    public void OptionParseTest(){ // Trzeba modyfikować testy aby pasowały do properties.json
        try {
            OptionParser options = new OptionParser();
            assertEquals(20, mapWidth);
            assertEquals(20, mapHeight);
            assertEquals(50, startEnergy);
            assertEquals(50, startAnimalNumber);
            assertEquals(1, moveEnergy);
            assertEquals(10, plantEnergy);
            assertEquals(30, jungleRatioWidth);
            assertEquals(30, jungleRatioHeight);
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
}
