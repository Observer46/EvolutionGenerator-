package agh.cs.project.lab8.Classes;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    int[] genes=new int[32];

    public Genotype (){
        for(int i=0; i<32;i++){
            genes[i]= new Random().nextInt(8);

        }
        fixGenes();
        Arrays.sort(genes);
    }
    public Genotype(Genotype other1,Genotype other2){
        int[] mixedGenes=other1.mixedGenes(other2);
        Arrays.sort(mixedGenes);
        this.genes=mixedGenes;
    }

    public int[] getGenes(){
        return this.genes;
    }
    public void setGenes(int[] genes){
        this.genes=genes;
    }


    private void fixGenes(){
        int [] geneCounter = new int[8];

        for(int gene : genes){
            geneCounter[gene]++;
        }

        for(int i=0; i<8;i++){
            int geneCount = geneCounter[i];
            if (geneCount==0)
                fixSingleGene(geneCounter,i);
        }
    }

    private void fixSingleGene(int[] geneCoutner, int gene){
        int geneToReplace;
        int previousGene;
        do {
            geneToReplace=new Random().nextInt(32);
            previousGene=genes[geneToReplace];  // Poprzedni gen
        }while(geneCoutner[previousGene]<2);    // Wymieniamy, tylko jeśli nie spowoduje to dodatkowej kolizji - gdy występuje więcej niż 1 raz
        geneCoutner[previousGene]--;
        geneCoutner[gene]++;
        genes[geneToReplace]=gene;
    }

    public int[] mixedGenes(Genotype other){
        int geneSplitter1=new Random().nextInt(32); // Dzielą geny na części, które będą wymieniane
        int geneSplitter2;
        do {
            geneSplitter2=new Random().nextInt(32);
        }while (geneSplitter1==geneSplitter2);

        if (geneSplitter1> geneSplitter2)       //Zamieniamy przy złej kolejności (nieczytelnie <3)
            geneSplitter1=geneSplitter1^=geneSplitter2^(geneSplitter2=geneSplitter1);
        boolean dominating=new Random().nextBoolean();
        int[] DNApart1;
        int[] DNApart2;
        int[] DNApart3;
        if(dominating){
            DNApart1=Arrays.copyOfRange(this.genes,0,geneSplitter1);
            DNApart2=Arrays.copyOfRange(other.genes,geneSplitter1,geneSplitter2);
            DNApart3=Arrays.copyOfRange(this.genes,geneSplitter2,32);
        }
        else{
            DNApart1=Arrays.copyOfRange(other.genes,0,geneSplitter1);
            DNApart2=Arrays.copyOfRange(this.genes,geneSplitter1,geneSplitter2);
            DNApart3=Arrays.copyOfRange(other.genes,geneSplitter2,32);
        }

        return combineGenes(DNApart1,DNApart2,DNApart3);

    }

    private static int[] combineGenes(int[] DNApart1, int[] DNApart2, int[] DNApart3){
        int[] combinedGenes= new int[32];
        int i=0;
        for(int gene : DNApart1)
            combinedGenes[i++]=gene;
        for(int gene : DNApart2)
            combinedGenes[i++]=gene;
        for(int gene : DNApart3)
            combinedGenes[i++]=gene;
        return combinedGenes;

    }

    public int getMove(){
        int direction=new Random().nextInt(32);
        return genes[direction];
    }

}
