package agh.cs.project.lab8.Classes;


public class Plant extends AbstractWorldMapElement {
    public static int energyBoost;
    public ModuloMap map;
    public Plant(Vector2d position, ModuloMap map){
        super.position=position;
        this.energyBoost= OptionParser.plantEnergy;
        this.map=map;
    }

    @Override
    public boolean isPlant() {
        return true;
    }

    public String toString(){
        String s;
        if(this.map.isInJungle(super.getPosition()))
            s= Character.toString((char) 10047)+ " ";
        else
            s= Character.toString((char) 10051)+ " ";
        return s;
    }
}
