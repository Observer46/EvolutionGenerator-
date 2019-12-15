import agh.cs.project.lab8.Classes.Genotype;
import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenotypeTest {             // Trochę trudno testować losowe funkcje, więc na czas testów musimy przyjąć ustalone próbki
    Genotype genotype1= new Genotype();
    Genotype genotype2= new Genotype();
//    int[] testGenes1={0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,6,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7};
//    int[] testGenes2={0,1,2,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,6,7};
    @Test
        public void areSortedGenesTest(){
        boolean areSorted=true;
        int[] genes=genotype1.getGenes();
        for(int i=1;i<32;i++)
            if(genes[i-1]>genes[i])
                areSorted=false;
        Assertions.assertTrue(areSorted);

        areSorted=true;
        Genotype mixTest = new Genotype(genotype1,genotype2);
        genes=mixTest.getGenes();
        for(int i=1;i<32;i++)
            if(genes[i-1]>genes[i])
                areSorted=false;
        Assertions.assertTrue(areSorted);


    }
}
