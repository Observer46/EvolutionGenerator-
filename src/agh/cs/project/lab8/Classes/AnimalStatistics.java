package agh.cs.project.lab8.Classes;

import java.util.ArrayList;
import java.util.List;

public class AnimalStatistics {
    private int childrenNumber=0;
    private int erasSurvived =0;
    private int descendantsNumber=0;
    private String genotypeString;
    private EvolvingAnimal animal;
    private List<EvolvingAnimal> children = new ArrayList<>();
    private int deathDate;

    public AnimalStatistics(EvolvingAnimal animal){
        this.animal=animal;
        this.genotypeString=animal.getGenotype().toString();
    }

    public void setDeathDate(int deathEra){
        this.deathDate=deathEra;
    }

    public int getErasSurvived(){
        return this.erasSurvived;
    }

    public int getChildrenNumber(){
        return this.childrenNumber;
    }

    public void dailyUpdate(){
        erasSurvived++;
        descendantsNumber=children.size();
        for (EvolvingAnimal child : children)
            descendantsNumber+=child.getAnimalStats().descendantsNumber;

    }
    public void becameAParent(EvolvingAnimal child){
        childrenNumber++;
        children.add(child);
    }

    public String printObservedStats(){
        String s = "Observer animal stats:\n";
        s+="Genotype: " + this.genotypeString + "\n";
        s+="Children number: " + this.childrenNumber + "\n";
        s+="Descendants number" + this.descendantsNumber + "\n";
        if(this.animal.checkIfLives())  s+="Status: alive";
        else    s+="Status: died on " + this.deathDate + "\n";
        return s;
    }



}
