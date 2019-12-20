import agh.cs.project.lab8.Classes.OptionParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static agh.cs.project.lab8.Classes.OptionParser.*;
public class OptionParserTest {
    @Test
    public void OptionParseTest(){ // Na razie trzeba modyfikować testy aby pasowały do properties.json
        OptionParser options = new OptionParser();
        Assert.assertEquals(200, mapWidth);
        Assert.assertEquals(100, mapHeight);
        Assert.assertEquals(30,startEnergy);
        Assert.assertEquals(10, startAnimalNumber);
        Assert.assertEquals(1,moveEnergy);
        Assert.assertEquals(10,plantEnergy);
        Assert.assertEquals(10,jungleRatioWidth);
        Assert.assertEquals(10,jungleRatioHeight);
    }
}
