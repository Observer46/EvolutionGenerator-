package agh.cs.project.lab8.Classes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStatistics {
    private ModuloMap map;
    private Map<Genotype,Integer> dominatingGenotypes = new HashMap<>();
    private Genotype mostPopularGenotype;
    private String mostPopularGenotypeString ="";
    private int mostPopularGenotypeCount=0;
    private int era=0;
    private DecimalFormat numberFormat = new DecimalFormat("0.00");
    private List <EvolvingAnimal> animalsWithDominatingGenotype=new ArrayList<>();

    public MapStatistics(ModuloMap map){
        this.map = map;
    }

    public int getAnimalCount(){
        return map.getAnimals().size();
    }

    public int getEra(){
        return this.era;
    }

    public int getPlantNumber(){
        return map.getPlants().size();
    }


    public void eraPassed(){
        this.era++;
        for(EvolvingAnimal animal : this.map.getAnimals()){
            AnimalStatistics animalStats = animal.getAnimalStats();
            animalStats.dailyUpdate();
        }
    }

    public List<EvolvingAnimal> getAnimalsWithDominatingGenotype() {
        return animalsWithDominatingGenotype;
    }

    public void addAnimalToStats(EvolvingAnimal animal){
        Genotype animalsGenotype = animal.getGenotype();
        int genotypeCount;
        if(!this.dominatingGenotypes.containsKey(animalsGenotype)) {
            genotypeCount = 1;
            dominatingGenotypes.put(animalsGenotype,genotypeCount);
        }
        else{
            genotypeCount = this.dominatingGenotypes.get(animalsGenotype);
            genotypeCount++;
        }
        if(genotypeCount>this.mostPopularGenotypeCount){
            this.mostPopularGenotype=animalsGenotype;
            this.mostPopularGenotypeCount = genotypeCount;
            this.mostPopularGenotypeString = animalsGenotype.toString();
            this.animalsWithDominatingGenotype.clear();
            this.addAnimalsWithDominatingGenotypes();
        }
        else if(animal.getGenotype().equals(this.mostPopularGenotype))
            this.animalsWithDominatingGenotype.add(animal);

    }

    public void addAnimalsWithDominatingGenotypes(){
        List <EvolvingAnimal> allAnimals = this.map.getAnimals();
        for(EvolvingAnimal animal : allAnimals)
            if(animal.getGenotype().equals(this.mostPopularGenotype))
                this.animalsWithDominatingGenotype.add(animal);

    }

    public double avgDeadAnimalsErasLived (){
        List<EvolvingAnimal> deadAnimals = this.map.getDeadAnimals();
        int totalSum =0;
        for(EvolvingAnimal deadAnim : deadAnimals){
            int erasLived = deadAnim.getAnimalStats().getErasSurvived();
            totalSum+=erasLived;
        }
        double avg = totalSum;
        if(deadAnimals.size()!=0)   avg/=deadAnimals.size();
        return avg;

    }

    public double avgEnergy(){
        List<EvolvingAnimal> aliveAnimals = this.map.getAnimals();
        int totalSum =0;
        for(EvolvingAnimal animal : aliveAnimals){
            int energy = animal.getEnergy();
            totalSum+=energy;
        }
        double avg = totalSum;
        if(aliveAnimals.size()!=0)   avg/=aliveAnimals.size();
        return avg;
    }

    public double avgChildren(){
        List<EvolvingAnimal> aliveAnimals = this.map.getAnimals();
        int totalSum =0;
        for(EvolvingAnimal animal : aliveAnimals){
            int childrenNumber = animal.getAnimalStats().getChildrenNumber();
            totalSum+=childrenNumber;
        }
        double avg = totalSum;
        if(aliveAnimals.size()!=0)   avg/=aliveAnimals.size();
        return avg;
    }

//    public void removeAnimalFromStats(EvolvingAnimal animal){
//        Genotype animalsGenotype = animal.getGenotype();
//        Integer genotypeCount = this.dominatingGenotypes.get(animalsGenotype);
//        genotypeCount--;
//    }
    public String getMostPopularGenotypeString(){
        return this.mostPopularGenotypeString;
    }

    public String toString(){
        this.era++;
        String description = "Stats after era " + this.era + ":\n";
        description+="Number of alive animals: " + this.getAnimalCount() + "\n";
        description+="Number of plants on map: " + this.getPlantNumber() + "\n";
        description+="Dominating genotype: " + this.getMostPopularGenotypeString() + "\n";
        description+="Averge energy of alive animals: " + numberFormat.format(this.avgEnergy()) + "\n";
        description+="Averge number of lived eras of dead animals: " + numberFormat.format(this.avgDeadAnimalsErasLived()) + "\n";
        description+="Averge children number for alive animals: " + numberFormat.format(this.avgChildren()) + "\n";
        return description;
    }
}
