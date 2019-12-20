import agh.cs.project.lab8.Classes.Genotype;
import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenotypeTest {             // Trochę trudno testować losowe funkcje, więc na czas testów musimy przyjąć ustalone próbki
    Genotype genotype1= new Genotype();
    Genotype genotype2= new Genotype();
    int[] testGenes1={0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,6,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7};
    int[] testGenes2={0,1,2,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,6,7};
    @Test
    public void areSortedGenesTest(){
        boolean areSorted=true;
        int[] genes=genotype1.getGenes();
        for(int i=1;i<Genotype.genoSize;i++)
            if(genes[i-1]>genes[i])
                areSorted=false;
        Assertions.assertTrue(areSorted);
        Genotype testGeneotype = genotype1.mixGenotype(genotype2);
        areSorted=true;
        genes=testGeneotype.getGenes();
        for(int i=1;i<Genotype.genoSize;i++)
            if(genes[i-1]>genes[i])
                areSorted=false;
        Assertions.assertTrue(areSorted);
    }

    @Test
    public void areCompleteGenesTest(){ // Sprawdzamy, czy każdy gen występuje
        //boolean areComplete=false;
        boolean[] genesChecked = new boolean[8];
        for(int i=0;i<8;i++)    genesChecked[i]=false;
        int[] genes1=genotype1.getGenes();
        for(int i=0;i<Genotype.genoSize;i++)    genesChecked[genes1[i]]=true; // Zaznaczamy które geny występują
        for(int i=0;i<8;i++){
            Assert.assertTrue(genesChecked[i]);
            genesChecked[i]=false;
        }
        Genotype testGenotype = genotype1.mixGenotype(genotype2);
        int[] genes2=testGenotype.getGenes();
        for(int i=0;i<Genotype.genoSize;i++)    genesChecked[genes2[i]]=true; // Zaznaczamy które geny występują
        for(int i=0;i<8;i++)
            Assert.assertTrue(genesChecked[i]);
    }

    @Test
    public void fixSingleGeneTest(){
        int[] testGenes={1,1,2,3,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,5,6,7};
        Genotype testGenotype = new Genotype(testGenes);
        int [] geneCounter = {0,2,1,1,25,1,1,1};
        testGenotype.fixSelectedSingleGene(1, geneCounter, 0); // Pod indeksem 1 zamieniamy gen 1 na 0
        Assert.assertEquals(0, testGenotype.getGenes()[1]);

    }

    @Test
    public void fixGenesTest(){
        int[] testGenes={7,7,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1,1,1,3,3,1,1,1,0};
        Genotype testGenotype = new Genotype(testGenes);
        testGenotype.fixGenes(); // Teraz kolejność genów powinna być poprawna
        boolean[] genesChecked = new boolean[8];
        for(int i=0;i<8;i++)    genesChecked[i]=false;
        int[] genesToTest=testGenotype.getGenes();
        for(int i=0;i<Genotype.genoSize;i++)    genesChecked[genesToTest[i]]=true; // Zaznaczamy które geny występują - się wykrzacza
        for(boolean is : genesChecked) System.out.print(is + " ");
        for(int i=0;i<8;i++)
            Assert.assertTrue(genesChecked[i]);
        for(int i=1; i<Genotype.genoSize;i++)
            Assert.assertTrue(genesToTest[i-1]<genesToTest[i]);

    }
}
