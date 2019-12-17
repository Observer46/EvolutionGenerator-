package agh.cs.project.lab8.Classes;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public static int genoSize=32;
    private int[] genes=new int[genoSize];


    public Genotype (){
        for(int i=0;i<8;i++)    genes[i]=i;
        for(int i=8; i<genoSize;i++){
            genes[i]= new Random().nextInt(8);
        }
        //this.fixGenes();
        Arrays.sort(genes);
    }
    public Genotype(int[] mixedGenes){
        this.genes=mixedGenes;
        this.fixGenes();

        //this.genes=mixedGenes;
    }

    public int[] getGenes(){
        return this.genes;
    }
    public void setGenes(int[] genes){ // Funkcja potrzebna do testów aby móc weryfikować wyniki
        this.genes=genes;
    }


    public void fixGenes(){
        int [] geneCounter = new int[8];

        for(int gene : genes){
            geneCounter[gene]++;
        }

        for(int i=0; i<8;i++){
            int geneCount = geneCounter[i];
            if (geneCount==0)
                fixSingleGene(geneCounter,i);
        }
        Arrays.sort(genes);
    }

    public void fixSelectedSingleGene(int indexToReplace, int[] geneCoutner, int gene){
        int previousGene=genes[indexToReplace];
        geneCoutner[previousGene]--;
        geneCoutner[gene]++;
        genes[indexToReplace]=gene;
    }

    public void fixSingleGene(int[] geneCoutner, int gene){
        int indexToReplace;
        int previousGene;
        do {
            indexToReplace=new Random().nextInt(genoSize);
            previousGene=genes[indexToReplace];  // Gen występujący w miejscu gdzie chcemy podmieniać
        }while(geneCoutner[previousGene]<2);    // Wymieniamy, tylko jeśli nie spowoduje to dodatkowej kolizji - gdy występuje więcej niż 1 raz
        fixSelectedSingleGene(indexToReplace,geneCoutner,gene);
    }

    public Genotype mixGenesOnGivenPivots(Genotype other, int geneSplitter1, int geneSplitter2){
        if (geneSplitter1> geneSplitter2)       //Zamieniamy przy złej kolejności (nieczytelnie <3)
            geneSplitter1=geneSplitter1^=geneSplitter2^(geneSplitter2=geneSplitter1);
        boolean iAmDominating=new Random().nextBoolean();
        int[] DNApart1;
        int[] DNApart2;
        int[] DNApart3;
        int[] dominatingGenes=this.genes;
        int[] recesiveGenes=other.genes;
        if(!iAmDominating){
            dominatingGenes=other.genes;
            recesiveGenes=this.genes;
        }
        DNApart1=Arrays.copyOfRange(dominatingGenes,0,geneSplitter1);
        DNApart2=Arrays.copyOfRange(recesiveGenes,geneSplitter1,geneSplitter2);
        DNApart3=Arrays.copyOfRange(dominatingGenes,geneSplitter2,genoSize);
        return new Genotype(combineGenes(DNApart1,DNApart2,DNApart3));
    }

    public Genotype mixGenotype(Genotype other){
        int geneSplitter1=new Random().nextInt(genoSize); // Dzielą geny na części, które będą wymieniane
        int geneSplitter2;
        do {
            geneSplitter2=new Random().nextInt(genoSize);
        }while (geneSplitter1==geneSplitter2);
        return this.mixGenesOnGivenPivots(other, geneSplitter1, geneSplitter2);
    }

    private static int[] combineGenes(int[] DNApart1, int[] DNApart2, int[] DNApart3){
        int[] combinedGenes= new int[genoSize];
        int i=0;
        for(int gene : DNApart1)
            combinedGenes[i++]=gene;
        for(int gene : DNApart2)
            combinedGenes[i++]=gene;
        for(int gene : DNApart3)
            combinedGenes[i++]=gene;
        return combinedGenes;

    }

    public MoveDirection getMove(){
        int direction=new Random().nextInt(genoSize);
        return MoveDirection.geneToMove(genes[direction]);
    }

}
